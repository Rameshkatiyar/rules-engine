//package com.tech.langParser;
//
//import com.google.common.base.Splitter;
//import com.google.common.collect.Lists;
//import com.tech.dslResolver.DSLKeywordResolver;
//import com.tech.dslResolver.DSLResolver;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//public class OldDSLResolver {
//    @Autowired
//    private DSLKeywordResolver keywordResolver;
//    @Autowired
//    private SpelParser spelParser;
//
//    private static final Pattern DSL_PATTERN = Pattern.compile("\\$\\((\\w+)((\\.\\w+)?)((=\\w+)?)\\)");
//    private static final Pattern GET_INPUT_PATTERN = Pattern.compile("\\$\\(input((\\.\\w+))\\)");
//    private static final Pattern RESULT_OUTPUT_PATTERN = Pattern.compile("\\$\\((\\w+)((\\.\\w+)?)((=\\w+)?)\\)");
//    private static final Pattern SET_PATTERN = Pattern.compile("#\\((\\w+)((=\\w+))\\)");
//
//    private static final String DOT = ".";
//    private static final String SET_ASSIGNMENT = "=";
//    private static final String INPUT_DATA_RESOLVER = "input";
//    private static final String RESULT_DATA_RESOLVER = "result";
//
//
//    //$(result.amount)={$(input.interest) * 5} then $(result.actual)={$(result.amount) - 1000} then $(result.approved)={true}
//
//    public void resolveSetOutputResultSpelExpression(String expression, Object inputData, Object outputResult){
//        List<String> listOfSetExpressionInSequence = Splitter.on("then").omitEmptyStrings().trimResults().splitToList(expression);
//        log.info("Output Result: {}", listOfSetExpressionInSequence);
//    }
//
//    public String resolveDomainSpecificKeywords(String expression, Object data){
//        Map<String, Object> dslKeywordToResolverValueMap = executeDSLResolver(expression, data);
//        return replaceKeywordsWithValue(expression, dslKeywordToResolverValueMap);
//    }
//
//    public String resolveGetInputDataVariable(String expression, Object inputData){
//        Map<String, Object> dslKeywordToResolverValueMap = executeGetInputDataVariableResolver(expression, inputData);
//        return replaceKeywordsWithValue(expression, dslKeywordToResolverValueMap);
//    }
//
//    public Map<String, Object> executeDSLExpression(String expression, Object inputData){
//        Map<String, Object> dslKeywordToResolverValueMap = executeDSLResolver(expression, inputData);
//        return dslKeywordToResolverValueMap;
//    }
//
//    private Map<String, Object> executeGetInputDataVariableResolver(String expression, Object inputData) {
//        List<String> listOfInputDataVariables = getListOfDslKeywords(expression);
//        Map<String, Object> inputDataVariableToResolverValueMap = new HashMap<>();
//        listOfInputDataVariables.stream()
//                .forEach(
//                        inputDataVariable -> {
//                            String extractDslKeyword = extractKeyword(inputDataVariable);
//                            String keyResolver = getKeywordResolver(extractDslKeyword);
//                            String keywordValue = getKeywordValue(extractDslKeyword);
//
//                            if (INPUT_DATA_RESOLVER.equalsIgnoreCase(keyResolver)){
//                                Object value = spelParser.getValue(inputData, keywordValue);
//                                inputDataVariableToResolverValueMap.put(inputDataVariable, value);
//                            }
//                        }
//                );
//        return inputDataVariableToResolverValueMap;
//    }
//
//    private Map<String, Object> executeDSLResolver(String expression, Object inputData) {
//        List<String> listOfDslKeyword = getListOfDslKeywords(expression);
//        Map<String, Object> dslKeywordToResolverValueMap = new HashMap<>();
//        listOfDslKeyword.stream()
//                .forEach(
//                        dslKeyword -> {
//                            String extractDslKeyword = extractKeyword(dslKeyword);
//                            String keyResolver = getKeywordResolver(extractDslKeyword);
//                            String keywordValue = getKeywordValue(extractDslKeyword);
//                            String setValue = getSetValue(extractDslKeyword);
//                            DSLResolver resolver = keywordResolver.getResolver(keyResolver).get();
//                            Object resolveValue = resolver.resolveValue(keywordValue);
//                            dslKeywordToResolverValueMap.put(dslKeyword, resolveValue);
//                        }
//                );
//        return dslKeywordToResolverValueMap;
//    }
//
//    private String replaceKeywordsWithValue(String expression, Map<String, Object> dslKeywordToResolverValueMap){
//        List<String> keyList = dslKeywordToResolverValueMap.keySet().stream().collect(Collectors.toList());
//        for (int index = 0; index < keyList.size(); index++){
//            String key = keyList.get(index);
//            String dslResolveValue = dslKeywordToResolverValueMap.get(key).toString();
//            expression = expression.replace(key, dslResolveValue);
//        }
//        return expression;
//    }
//
//    private String getKeywordResolver(String dslKeyword){
//        ArrayList<String> splittedKeyword = Lists.newArrayList(Splitter.on(DOT).omitEmptyStrings().split(dslKeyword));
//        return splittedKeyword.get(0);
//    }
//
//    private String getKeywordValue(String dslKeyword){
//        ArrayList<String> splitedKeywords = Lists.newArrayList(Splitter.on(DOT).omitEmptyStrings().split(dslKeyword));
//        if (splitedKeywords.size() != 2){
//            return splitedKeywords.get(0);
//        }
//        String secondKeyword = splitedKeywords.get(1);
//        if (secondKeyword.contains(SET_ASSIGNMENT)){
//            return Lists.newArrayList(Splitter.on(SET_ASSIGNMENT).omitEmptyStrings().split(secondKeyword)).get(0);
//        }
//        return secondKeyword;
//    }
//
//    private String getSetValue(String dslKeyword){
//        ArrayList<String> splitedKeywords = Lists.newArrayList(Splitter.on(DOT).omitEmptyStrings().split(dslKeyword));
//        if (splitedKeywords.size() != 2){
//            return splitedKeywords.get(0);
//        }
//        String secondKeyword = splitedKeywords.get(1);
//        if (secondKeyword.contains(SET_ASSIGNMENT)){
//            return Lists.newArrayList(Splitter.on(SET_ASSIGNMENT).omitEmptyStrings().split(secondKeyword)).get(1);
//        }
//        return null;
//    }
//
//    private List<String> getListOfDslKeywords(String expression) {
//        Matcher matcher = DSL_PATTERN.matcher(expression);
//        List<String> listOfDslKeyword = new ArrayList<>();
//        while (matcher.find()) {
//            String group = matcher.group();
//            listOfDslKeyword.add(group);
//        }
//        return listOfDslKeyword;
//    }
//
//    private List<String> getListOfSetDslKeywords(String expression) {
//        Matcher matcher = SET_PATTERN.matcher(expression);
//        List<String> listOfDslKeyword = new ArrayList<>();
//        while (matcher.find()) {
//            String group = matcher.group();
//            listOfDslKeyword.add(group);
//        }
//        return listOfDslKeyword;
//    }
//
//    private List<String> getListOfGetDslKeywords(String expression) {
//        Matcher matcher = GET_INPUT_PATTERN.matcher(expression);
//        List<String> listOfDslKeyword = new ArrayList<>();
//        while (matcher.find()) {
//            String group = matcher.group();
//            listOfDslKeyword.add(group);
//        }
//        return listOfDslKeyword;
//    }
//
//    public static void main(String args[]){
//        OldDSLResolver oldDslResolver = new OldDSLResolver();
//        String expression = "#( =$(input.abc)) then #(arg=3s4)";
//
//        List<String> listOfDslKeywords = oldDslResolver.getListOfGetDslKeywords(expression);
//        String extractKeyword = oldDslResolver.extractKeyword(listOfDslKeywords.get(0));
//        System.out.print(oldDslResolver.getKeywordValue(extractKeyword));
//    }
//
//    private String extractKeyword(String keyword) {
//        return keyword.substring(keyword.indexOf('(') + 1,
//                        keyword.indexOf(')'));
//    }
//}
