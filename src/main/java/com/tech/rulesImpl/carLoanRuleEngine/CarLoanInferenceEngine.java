package com.tech.rulesImpl.carLoanRuleEngine;

import com.tech.ruleEngine.InferenceEngine;
import com.tech.rulesImpl.carLoanRuleEngine.model.LoanDetailsOutputResult;
import com.tech.rulesImpl.carLoanRuleEngine.model.UserInfoInputData;
import com.tech.rulesImpl.common.enums.RuleNamespace;
import com.tech.knowledgeBase.models.Rule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarLoanInferenceEngine extends InferenceEngine<UserInfoInputData, LoanDetailsOutputResult> {
    @Override
    protected LoanDetailsOutputResult executeRule(Rule rule, UserInfoInputData userInfoInputData) {
        return null;
    }

    @Override
    protected RuleNamespace getRuleNamespace() {
        return RuleNamespace.CAR_LOAN;
    }
}
