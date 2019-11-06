package com.tech.rulesImpl.carLoanRuleEngine.resolver;

import com.tech.languageResolver.Resolver;
import com.tech.rulesImpl.carLoanRuleEngine.model.LoanDetailsOutputResult;
import com.tech.rulesImpl.carLoanRuleEngine.model.UserInfoInputData;

public class LoanKeywordResolver implements Resolver<UserInfoInputData, LoanDetailsOutputResult> {
    private static final String RESOLVER_KEYWORD = "loandetail";

    private static final String CIBIL_SCORE = "cibil_score";

    @Override
    public String getResolverKeyword() {
        return RESOLVER_KEYWORD;
    }

    @Override
    public Object resolveValue(String keyword, UserInfoInputData inputData, LoanDetailsOutputResult outputResult) {
        if (keyword.equalsIgnoreCase(CIBIL_SCORE)){
            return (inputData.getCreditScore() + 100);
        }
        return null;
    }
}
