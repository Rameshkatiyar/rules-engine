package com.tech.rulesImpl.insuranceRuleEngine;

import com.tech.restAPI.RuleNamespace;
import com.tech.ruleEngine.InferenceEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InsuranceInferenceEngine extends InferenceEngine<PolicyHolderDetails, InsuranceDetails> {

    @Override
    protected RuleNamespace getRuleNamespace() {
        return RuleNamespace.INSURANCE;
    }

    @Override
    protected InsuranceDetails initializeOutputResult() {
        return InsuranceDetails.builder().build();
    }
}
