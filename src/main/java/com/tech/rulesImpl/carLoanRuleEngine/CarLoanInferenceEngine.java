package com.tech.rulesImpl.carLoanRuleEngine;

import com.tech.restAPI.RuleNamespace;
import com.tech.ruleEngine.InferenceEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarLoanInferenceEngine extends InferenceEngine<UserInfoInputData, LoanDetailsOutputResult> {

    @Override
    protected RuleNamespace getRuleNamespace() {
        return RuleNamespace.CAR_LOAN;
    }

    @Override
    protected LoanDetailsOutputResult initializeOutputResult() {
        return LoanDetailsOutputResult.builder().build();
    }
}
