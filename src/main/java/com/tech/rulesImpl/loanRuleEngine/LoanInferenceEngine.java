package com.tech.rulesImpl.loanRuleEngine;

import com.tech.ruleEngine.InferenceEngine;
import com.tech.rulesImpl.common.enums.RuleNamespace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoanInferenceEngine extends InferenceEngine<Loan, Loan> {

//    @Override
//    public Loan executeRule(Rule rule, Loan inputData){
//        String action = rule.getAction();
//        dslResolver.executeDSLExpression(action, inputData);
//        return inputData;
//    }

    @Override
    protected RuleNamespace getRuleNamespace() {
        return RuleNamespace.LOAN;
    }

    @Override
    protected Loan initializeOutputResult() {
        return Loan.builder().build();
    }
}
