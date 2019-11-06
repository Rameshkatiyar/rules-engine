package com.tech.rulesImpl.carLoanRuleEngine.resolver;

import com.tech.languageResolver.Resolver;
import com.tech.rulesImpl.carLoanRuleEngine.model.LoanDetailsOutputResult;
import com.tech.rulesImpl.carLoanRuleEngine.model.UserInfoInputData;

import java.time.LocalDate;

public class UserKeywordResolver implements Resolver<UserInfoInputData, LoanDetailsOutputResult> {
    @Override
    public String getResolverKeyword() {
        return "user";
    }

    @Override
    public Object resolveValue(String keyword, UserInfoInputData inputData, LoanDetailsOutputResult outputResult) {
        if (keyword.equalsIgnoreCase("age")){
            LocalDate dob = inputData.getDob();
            return dob.getDayOfYear();
        }
        return EMPTY;
    }
}
