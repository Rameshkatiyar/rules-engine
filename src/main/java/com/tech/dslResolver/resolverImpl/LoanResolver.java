package com.tech.dslResolver.resolverImpl;

import com.tech.dslResolver.DSLResolver;
import org.springframework.stereotype.Component;

@Component
public class LoanResolver implements DSLResolver {
    private static final String RESOLVER_KEYWORD = "loan";
    private static final String CIBIL_SCORE = "score";
    private static final String INTEREST = "interest";

    @Override
    public String getResolverKeyword() {
        return RESOLVER_KEYWORD;
    }

    @Override
    public Object resolveValue(String keyword) {
        if (keyword.equalsIgnoreCase(CIBIL_SCORE)){
            return 500;
        }
        if (keyword.equalsIgnoreCase(INTEREST)){
            return 9.0;
        }
        return null;
    }
}
