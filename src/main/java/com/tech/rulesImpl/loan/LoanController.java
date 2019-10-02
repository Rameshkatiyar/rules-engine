package com.tech.rulesImpl.loan;

import com.tech.enums.RuleNamespace;
import com.tech.models.Rule;
import com.tech.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class LoanController {

    @Autowired
    private DataService dataService;
    @Autowired
    private LoanInferenceEngine loanInferenceEngine;

    @PostMapping(value = "/loan")
    public ResponseEntity<?> postUserLoanDetails(@RequestBody Loan loan) {
        List<Rule> allRules = dataService.getAllRuleByNamespace(RuleNamespace.LOAN.toString());
        Loan loanStatus = loanInferenceEngine.run(allRules, loan);
        return ResponseEntity.ok(loanStatus);
    }
}
