package com.tech.rulesImpl.carLoanRuleEngine.resolver;

import com.tech.languageResolver.Resolver;
import com.tech.rulesImpl.carLoanRuleEngine.model.LoanDetailsOutputResult;

public class LoanKeywordResolver implements Resolver<LoanDetailsOutputResult> {
    @Override
    public String getResolverKeyword() {
        return "loandetail";
    }

    @Override
    public Object resolveValue(String keyword, LoanDetailsOutputResult inputData, Object setValue) {
        return null;
    }
}
