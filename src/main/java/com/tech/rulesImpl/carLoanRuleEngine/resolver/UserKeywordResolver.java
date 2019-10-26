package com.tech.rulesImpl.carLoanRuleEngine.resolver;

import com.tech.languageResolver.Resolver;
import com.tech.rulesImpl.carLoanRuleEngine.model.UserInfoInputData;

public class UserKeywordResolver implements Resolver<UserInfoInputData> {
    @Override
    public String getResolverKeyword() {
        return "user";
    }

    @Override
    public Object resolveValue(String keyword, UserInfoInputData inputData, Object setValue) {
        return null;
    }
}
