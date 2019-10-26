package com.tech.rulesImpl.loanRuleEngine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    Integer creditScore;
    String firstName;
    Double requestedLoanAmount;
    Boolean approved;
    Double approvedAmount;
    Double salary;
}
