package com.tech.langParser;

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

    public Object parseSpelExpression(String spelExpression, Object data){
        try {
            standardEvaluationContext.setRootObject(data);
            Expression expression = spelExpressionParser.parseExpression(spelExpression);
            return expression.getValue(standardEvaluationContext);
        }catch (Exception e){
            log.error("Incorrect Spel expression: {} . Error: {}", spelExpression, e.getMessage());
            return null;
        }
    }

    public Object getValue(Object dataModel, String variableName){
        standardEvaluationContext.setRootObject(dataModel);
        Object value = spelExpressionParser.parseExpression(variableName).getValue(standardEvaluationContext);
        return value;
    }

    public void setValue(Object dataModel, String variableName, Object value){
        standardEvaluationContext.setRootObject(dataModel);
        spelExpressionParser.parseExpression(variableName).setValue(standardEvaluationContext, value);
    }
}
