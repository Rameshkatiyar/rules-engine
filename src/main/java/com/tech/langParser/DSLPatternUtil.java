package com.tech.langParser;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class DSLPatternUtil {
    private static final Pattern DSL_PATTERN = Pattern.compile("\\$\\((\\w+)(\\.\\w+)\\)"); //$(rulenamespace.keyword)
    private static final String DOT = ".";

    public List<String> getListOfDslKeywords(String expression) {
        Matcher matcher = DSL_PATTERN.matcher(expression);
        List<String> listOfDslKeyword = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            listOfDslKeyword.add(group);
        }
        return listOfDslKeyword;
    }

    public String extractKeyword(String keyword) {
        return keyword.substring(keyword.indexOf('(') + 1,
                keyword.indexOf(')'));
    }

    public String getKeywordResolver(String dslKeyword){
        ArrayList<String> splittedKeyword = Lists.newArrayList(Splitter.on(DOT).omitEmptyStrings().split(dslKeyword));
        return splittedKeyword.get(0);
    }

    public String getKeywordValue(String dslKeyword){
        ArrayList<String> splittedKeyword = Lists.newArrayList(Splitter.on(DOT).omitEmptyStrings().split(dslKeyword));
        return splittedKeyword.get(1);
    }
}
