package com.tech.languageResolver;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DSLResolver {
    @Autowired
    private KeywordResolver keywordResolver;

    private static final Pattern DSL_PATTERN = Pattern.compile("\\$\\((\\w+)((\\.\\w+)?)((=\\w+)?)\\)");
    private static final String DOT = ".";
    private static final String SET_ASSIGNMENT = "=";

    public String resolveDSLToSpelExpression(String expression, Object inputData){
        Map<String, Object> dslKeywordToResolverValueMap = executeDSLResolver(expression, inputData);
        return replaceDslKeywordsInSpelExpression(expression, dslKeywordToResolverValueMap);
    }

    public Map<String, Object> executeDSLExpression(String expression, Object inputData){
        Map<String, Object> dslKeywordToResolverValueMap = executeDSLResolver(expression, inputData);
        return dslKeywordToResolverValueMap;
    }

    private Map<String, Object> executeDSLResolver(String expression, Object inputData) {
        List<String> listOfDslKeyword = getListOfDslKeywords(expression);
        Map<String, Object> dslKeywordToResolverValueMap = new HashMap<>();
        listOfDslKeyword.stream()
                .forEach(
                        dslKeyword -> {
                            String extractDslKeyword = extractKeyword(dslKeyword);
                            String keyResolver = getKeywordResolverValue(extractDslKeyword);
                            String keywordValue = getKeywordValue(extractDslKeyword);
                            String setValue = getSetValue(extractDslKeyword);
                            Resolver resolver = keywordResolver.getResolver(keyResolver).get();
                            Object resolveValue = resolver.resolveValue(keywordValue, inputData, setValue);
                            dslKeywordToResolverValueMap.put(dslKeyword, resolveValue);
                        }
                );
        return dslKeywordToResolverValueMap;
    }

    private String replaceDslKeywordsInSpelExpression(String expression, Map<String, Object> dslKeywordToResolverValueMap){
        List<String> keyList = dslKeywordToResolverValueMap.keySet().stream().collect(Collectors.toList());
        for (int index = 0; index < keyList.size(); index++){
            String key = keyList.get(index);
            String dslResolveValue = dslKeywordToResolverValueMap.get(key).toString();
            expression = expression.replace(key, dslResolveValue);
        }
        return expression;
    }

    private String getKeywordResolverValue(String dslKeyword){
        ArrayList<String> splittedKeyword = Lists.newArrayList(Splitter.on(DOT).omitEmptyStrings().split(dslKeyword));
        return splittedKeyword.get(0);
    }

    private String getKeywordValue(String dslKeyword){
        ArrayList<String> splitedKeywords = Lists.newArrayList(Splitter.on(DOT).omitEmptyStrings().split(dslKeyword));
        if (splitedKeywords.size() != 2){
            return splitedKeywords.get(0);
        }
        String secondKeyword = splitedKeywords.get(1);
        if (secondKeyword.contains(SET_ASSIGNMENT)){
            return Lists.newArrayList(Splitter.on(SET_ASSIGNMENT).omitEmptyStrings().split(secondKeyword)).get(0);
        }
        return secondKeyword;
    }

    private String getSetValue(String dslKeyword){
        ArrayList<String> splitedKeywords = Lists.newArrayList(Splitter.on(DOT).omitEmptyStrings().split(dslKeyword));
        if (splitedKeywords.size() != 2){
            return splitedKeywords.get(0);
        }
        String secondKeyword = splitedKeywords.get(1);
        if (secondKeyword.contains(SET_ASSIGNMENT)){
            return Lists.newArrayList(Splitter.on(SET_ASSIGNMENT).omitEmptyStrings().split(secondKeyword)).get(1);
        }
        return null;
    }

    private List<String> getListOfDslKeywords(String expression) {
        Matcher matcher = DSL_PATTERN.matcher(expression);
        List<String> listOfDslKeyword = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            listOfDslKeyword.add(group);
        }
        return listOfDslKeyword;
    }

    private String extractKeyword(String keyword) {
        return keyword.substring(keyword.indexOf('(') + 1,
                        keyword.indexOf(')'));
    }
}
