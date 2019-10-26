package com.tech.ruleEngine;

import com.tech.languageResolver.DSLResolver;
import com.tech.languageResolver.SpelParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RuleParser<INPUT_DATA, OUTPUT_RESULT> {

    @Autowired
    protected DSLResolver dslResolver;
    @Autowired
    protected SpelParser spelParser;

    public boolean matchConditionOnInputData(String condition, INPUT_DATA inputData) {
        String spelExpression = dslResolver.resolveDSLToSpelExpression(
                condition, inputData);
        return spelParser.parseConditionExpression(
                spelExpression, inputData);
    }

    public OUTPUT_RESULT performActionOnInputData(String action, INPUT_DATA inputData) {
        dslResolver.executeDSLExpression(action, inputData);
//        return inputData;
        return null;
    }
}
