package com.tech.controller;

import com.tech.api.RuleEngine;
import com.tech.rulesImpl.loan.Loan;
import com.tech.rulesImpl.loan.LoanInferenceEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoanController {

    @Autowired
    private RuleEngine ruleEngine;
    @Autowired
    private LoanInferenceEngine loanInferenceEngine;

    @PostMapping(value = "/loan")
    public ResponseEntity<?> postUserLoanDetails(@RequestBody Loan loan) {
        Loan result = (Loan) ruleEngine.run(loanInferenceEngine, loan);
        return ResponseEntity.ok(result);
    }
}
