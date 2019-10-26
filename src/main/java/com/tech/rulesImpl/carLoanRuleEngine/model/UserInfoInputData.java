package com.tech.rulesImpl.carLoanRuleEngine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoInputData {
    String firstName;
    String lastName;
    LocalDate dob;
    Long accountNumber;
    Double salary;
    String bank;
    Integer creditScore;
    Double requestedLoanAmount;
}
