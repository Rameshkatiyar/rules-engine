package com.tech.langParser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RuleParser<INPUT_DATA, OUTPUT_RESULT> {

    @Autowired
    protected OldDSLResolver oldDslResolver;
    @Autowired
    protected SpelParser spelParser;

    /**
     * Parsing in given priority/steps.
     *
     * Step 1. Resolve domain specific keywords: $(rulenamespace.keyword)
     * Step 2. Resolve get input data variable value: $(input.variableName)
     * Step 3. Resolve SPEL expression.
     *
     * @param expression
     * @param inputData
     */
    public boolean parseCondition(String expression, INPUT_DATA inputData) {
        String resolvedDslExpression = oldDslResolver.resolveDomainSpecificKeywords(expression, inputData);
        String resolvedGetExpression = oldDslResolver.resolveGetInputDataVariable(resolvedDslExpression, inputData);
        Object value = spelParser.parseSpelExpression(resolvedGetExpression, inputData);
        if (null != value){
            return (boolean) value;
        }
        return false;
    }

    /**
     * Parsing in given priority/steps.
     *
     * Step 1. Resolve domain specific keywords: $(rulenamespace.keyword)
     * Step 2. Resolve get input data variable value: $(input.variableName)
     * Step 3. Resolve SPEL expression.
     * Step 4. Set value in output result object: #(variableName=value)
     *
     * $(result.amount)={$(input.interest) * 5} then $(result.actual)={$(result.amount) - 1000} then $(result.approved)={true}
     *
     * @param expression
     * @param inputData
     * @return
     */
    public OUTPUT_RESULT parseAction(String expression, INPUT_DATA inputData, OUTPUT_RESULT outputResult) {
        String resolvedDslExpression = oldDslResolver.resolveDomainSpecificKeywords(expression, inputData);
        String resolvedGetExpression = oldDslResolver.resolveGetInputDataVariable(resolvedDslExpression, inputData);
        oldDslResolver.resolveSetOutputResultSpelExpression(resolvedGetExpression, inputData, outputResult);
        return outputResult;
    }

}
