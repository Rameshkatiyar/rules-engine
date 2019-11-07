package com.tech.langParser;

import lombok.extern.slf4j.Slf4j;
import org.mvel2.MVEL;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class MVELParser {

    public boolean parseMvelExpression( String expression, Map<String, Object> inputObjects){
        try {
            return MVEL.evalToBoolean(expression,inputObjects);
        }catch (Exception e){
            log.error("Can not parse Mvel Expression : {} Error: {}", expression, e.getMessage());
        }
        return false;
    }


//    public static void main(String args[]){
//        Map<String, Object> input = new HashMap<>();
//        LoanDetailsOutputResult loan = new LoanDetailsOutputResult();
//        UserInfoInputData user = new UserInfoInputData();
//        user.setFirstName("Ramesh");
//        user.setRequestedLoanAmount(20.0);
//        user.setCreditScore(10);
//
//        input.put("loan", loan);
//        input.put("user", user);
//
////        String expression = "user.setRequestedLoanAmount(30.0 * 5 + (user.creditScore))";
//        String expression = "(30.0 * 5 + (user.creditScore)) > 40.0";
//
//
//        MVELParser mvelParser = new MVELParser();
//        boolean result = mvelParser.parseMvelExpression(expression, input);
//
//        System.out.println("Result: "+result);
//
//        System.out.println(user.getRequestedLoanAmount());
//    }
}
