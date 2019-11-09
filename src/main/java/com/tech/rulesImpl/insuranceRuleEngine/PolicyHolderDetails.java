package com.tech.rulesImpl.insuranceRuleEngine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyHolderDetails {
    String firstName;
    String lastName;
    LocalDate dob;
    String gender;
    Double premiumAmount;
    Integer policyTermInYear;
    Integer premiumTermInYear;
}
