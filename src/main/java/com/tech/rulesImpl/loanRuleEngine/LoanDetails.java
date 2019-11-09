package com.tech.rulesImpl.loanRuleEngine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetails {
    Long accountNumber;
    Boolean approvalStatus;
    Float interestRate;
    Float sanctionedPercentage;
    Double processingFees;
}
