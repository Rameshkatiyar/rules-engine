package com.tech.rulesImpl.loanRuleEngine;

import com.tech.restAPI.RuleNamespace;
import com.tech.ruleEngine.InferenceEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoanInferenceEngine extends InferenceEngine<Loan, Loan> {

    @Override
    protected RuleNamespace getRuleNamespace() {
        return RuleNamespace.LOAN;
    }

    @Override
    protected Loan initializeOutputResult() {
        return Loan.builder().build();
    }
}
