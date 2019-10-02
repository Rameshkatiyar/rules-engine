package com.tech.api;

import com.tech.models.Rule;
import com.tech.service.DSLResolver;
import com.tech.service.SpelParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public abstract class InferenceEngine<I, O> {

    @Autowired
    protected DSLResolver dslResolver;
    @Autowired
    protected SpelParser spelParser;

    /**
     * Run inference engine on set of rules for given data.
     * @param listOfRules
     * @param inputData
     * @return
     */
    public O run (List<Rule> listOfRules, I inputData){
        if (null == listOfRules || listOfRules.isEmpty()){
            return null;
        }
        List<Rule> conflictSet = match(listOfRules, inputData);

        Rule resolveRule = resolve(conflictSet);
        if (null == resolveRule){
            return null;
        }

        return executeRule(resolveRule, inputData);
    }

    /**
     * Using Leaner matching algo. We can use here any pattern matching algo.
     * 1. Rete
     * 2. Linear
     * 3. Treat
     * 4. Leaps
     * @param listOfRules
     * @param inputData
     * @return
     */
    protected List<Rule> match(List<Rule> listOfRules, I inputData){
        return listOfRules.stream()
                .filter(
                        rule -> {
                            String spelExpression = dslResolver.resolveDSLToSpelExpression(
                                    rule.getCondition(), inputData);
                            return spelParser.parseConditionExpression(
                                    spelExpression, inputData);
                        }
                )
                .collect(Collectors.toList());
    }

    /**
     * Using find first rule logic. We can use here any resolving techniques:
     * 1. Lex
     * 2. Recency
     * 3. MEA
     * 4. Refactor
     * 5. Priority wise
     * @param conflictSet
     * @return
     */
    protected Rule resolve(List<Rule> conflictSet){
        Optional<Rule> rule = conflictSet.stream()
                .findFirst();
        if (rule.isPresent()){
            return rule.get();
        }
        return null;
    }

    /**
     * Execute selected rule on input data.
     * @param rule
     * @param inputData
     * @return
     */
    protected abstract O executeRule(Rule rule, I inputData);
}
