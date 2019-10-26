package com.tech.rulesImpl.loanRuleEngine.resolver;

import com.tech.languageResolver.Resolver;
import com.tech.rulesImpl.loanRuleEngine.Loan;
import org.springframework.stereotype.Service;

@Service
public class LoanAmountResolver implements Resolver<Loan> {
    private static final String RESOLVER_KEYWORD = "loan";
    private static final String REQUESTED_AMOUNT = "requested_amount";

    private static final String APPROVED_PERCENTAGE = "Sanctioned_per";

    private static final String SALARY = "salary";
    private static final String CREDITSCORE = "credit_score";
    private static final String APPROVED_STATUS = "approved_status";

    @Override
    public String getResolverKeyword() {
        return RESOLVER_KEYWORD;
    }

    @Override
    public Object resolveValue(String keyword, Loan loan, Object setValue) {
        if (keyword.equalsIgnoreCase(SALARY)){
            return loan.getSalary();
        }

        if (keyword.equalsIgnoreCase(CREDITSCORE)){
            return loan.getCreditScore();
        }

        if (keyword.equalsIgnoreCase(REQUESTED_AMOUNT)){
            return loan.getRequestedLoanAmount();
        }
        if (keyword.equalsIgnoreCase(APPROVED_STATUS)){
            loan.setApproved(true);
            return loan;
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
