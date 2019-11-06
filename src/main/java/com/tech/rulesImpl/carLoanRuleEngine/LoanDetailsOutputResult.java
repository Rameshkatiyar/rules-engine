package com.tech.rulesImpl.carLoanRuleEngine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetailsOutputResult {
    Boolean approvalStatus;
    LocalDate approvalDate;
    Float interestRate;
    Double sanctionedAmount;
    Double processingFees;
}
