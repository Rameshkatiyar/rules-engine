package com.tech.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpelParser {

    @Autowired
    private StandardEvaluationContext standardEvaluationContext;
    @Autowired
    private SpelExpressionParser spelExpressionParser;

    public boolean parseConditionExpression(String spelExpression, Object inputData) {
        try {
            standardEvaluationContext.setRootObject(inputData);
            Expression expression = spelExpressionParser.parseExpression(spelExpression);
            return expression.getValue(standardEvaluationContext, Boolean.class);
        }catch (Exception e){
            log.error("Incorrect Spel expression: {} . Error: {}", spelExpression, e.getMessage());
            return false;
        }
    }
}
