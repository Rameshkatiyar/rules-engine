package com.tech.rulesImpl.loan.resolver;

import com.tech.api.Resolver;
import com.tech.rulesImpl.loan.Loan;
import org.springframework.stereotype.Service;

@Service
public class LoanAmountResolver implements Resolver<Loan> {
    private static final String RESOLVER_KEYWORD = "loan";
    private static final String REQUESTED_AMOUNT = "requested_amount";
    private static final String APPROVED_PERCENTAGE = "approved_percentage";

    @Override
    public String getResolverKeyword() {
        return RESOLVER_KEYWORD;
    }

    @Override
    public Object resolveValue(String keyword, Loan loan, Object setValue) {
        if (keyword.equalsIgnoreCase(REQUESTED_AMOUNT)){
            return loan.getRequestedLoanAmount();
        }
        if (keyword.equalsIgnoreCase(APPROVED_PERCENTAGE)){
            Double percentage = Double.parseDouble(setValue.toString());
            Double requestedLoanAmount = loan.getRequestedLoanAmount();
            loan.setApprovedAmount(requestedLoanAmount*percentage);
            loan.setApproved(true);
            return loan;
        }
        return EMPTY;
    }
}
