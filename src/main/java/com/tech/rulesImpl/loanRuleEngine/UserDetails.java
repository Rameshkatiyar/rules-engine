package com.tech.rulesImpl.loanRuleEngine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    String firstName;
    String lastName;
    Integer age;
    Long accountNumber;
    Double monthlySalary;
    String bank;
    Integer cibilScore;
    Double requestedLoanAmount;
}
