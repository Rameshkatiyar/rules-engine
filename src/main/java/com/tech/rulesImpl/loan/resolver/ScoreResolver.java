package com.tech.rulesImpl.loan.resolver;

import com.tech.api.Resolver;
import com.tech.rulesImpl.loan.Loan;
import org.springframework.stereotype.Service;

@Service
public class ScoreResolver implements Resolver<Loan> {
    private static final String RESOLVER_KEYWORD = "score";
    private static final String CREDIT_SCORE = "credit_score";

    @Override
    public String getResolverKeyword() {
        return RESOLVER_KEYWORD;
    }

    @Override
    public Object resolveValue(String keyword, Loan loan, Object setValue) {
        if (keyword.equalsIgnoreCase(CREDIT_SCORE)){
            return loan.getCreditScore();
        }
        return 0;
    }
}
