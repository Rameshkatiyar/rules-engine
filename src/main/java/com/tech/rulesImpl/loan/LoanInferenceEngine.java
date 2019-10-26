package com.tech.rulesImpl.loan;

import com.tech.api.InferenceEngine;
import com.tech.enums.RuleNamespace;
import com.tech.models.Rule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoanInferenceEngine extends InferenceEngine<Loan, Loan> {

    @Override
    public Loan executeRule(Rule rule, Loan inputData){
        String action = rule.getAction();
        dslResolver.executeDSLExpression(action, inputData);
        return inputData;
    }

    @Override
    protected RuleNamespace getRuleNamespace() {
        return RuleNamespace.LOAN;
    }
}
