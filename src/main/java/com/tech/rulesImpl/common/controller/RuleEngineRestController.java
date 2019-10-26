package com.tech.rulesImpl.common.controller;

import com.google.common.base.Enums;
import com.tech.ruleEngine.RuleEngine;
import com.tech.rulesImpl.common.enums.RuleNamespace;
import com.tech.knowledgeBase.models.Rule;
import com.tech.knowledgeBase.KnowledgeBaseService;
import com.tech.rulesImpl.carLoanRuleEngine.CarLoanInferenceEngine;
import com.tech.rulesImpl.carLoanRuleEngine.model.LoanDetailsOutputResult;
import com.tech.rulesImpl.carLoanRuleEngine.model.UserInfoInputData;
import com.tech.rulesImpl.loanRuleEngine.Loan;
import com.tech.rulesImpl.loanRuleEngine.LoanInferenceEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class RuleEngineRestController {
    @Autowired
    private KnowledgeBaseService knowledgeBaseService;
    @Autowired
    private RuleEngine ruleEngine;
    @Autowired
    private LoanInferenceEngine loanInferenceEngine;
    @Autowired
    private CarLoanInferenceEngine carLoanInferenceEngine;

    @GetMapping(value = "/get-all-rules/{ruleNamespace}")
    public ResponseEntity<?> getRulesByNamespace(@PathVariable("ruleNamespace") String ruleNamespace) {
        RuleNamespace namespace = Enums.getIfPresent(RuleNamespace.class, ruleNamespace.toUpperCase()).or(RuleNamespace.DEFAULT);
        List<Rule> allRules = knowledgeBaseService.getAllRuleByNamespace(namespace.toString());
        return ResponseEntity.ok(allRules);
    }

    @GetMapping(value = "/get-all-rules")
    public ResponseEntity<?> getAllRules() {
        List<Rule> allRules = knowledgeBaseService.getAllRules();
        return ResponseEntity.ok(allRules);
    }

    @PostMapping(value = "/loan")
    public ResponseEntity<?> postUserLoanDetails(@RequestBody Loan loan) {
        Loan result = (Loan) ruleEngine.run(loanInferenceEngine, loan);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/car-loan")
    public ResponseEntity<?> postCarLoanDetails(@RequestBody UserInfoInputData userInfoInputData) {
        LoanDetailsOutputResult result = (LoanDetailsOutputResult) ruleEngine.run(carLoanInferenceEngine, userInfoInputData);
        return ResponseEntity.ok(result);
    }
}
