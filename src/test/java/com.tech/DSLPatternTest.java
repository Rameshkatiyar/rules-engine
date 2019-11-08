package com.tech;

import com.tech.langParser.DSLPatternUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class) //This annotation is used to specify the application configuration to load before running the tests.
public class DSLPatternTest {

    @Autowired
    private DSLPatternUtil dslPatternUtil;

    final String expression1 = "input.salary >= 70000 && input.creditScore >= 900 && $(loan.interest) >= 8";
    final String expression2 = "$(input.salary) >= 70000 && input.creditScore >= 900 && $(loan.interest) >= 8";

    @Test
    public void verifyPatternInExpression(){
        String keyword = dslPatternUtil.getListOfDslKeywords(expression1).get(0);
        assertThat(keyword).isEqualTo("$(loan.interest)");
    }

    @Test
    public void verifyNumberOfPatternFoundInExpression(){
        int numberOfPatters = dslPatternUtil.getListOfDslKeywords(expression2).size();
        assertThat(numberOfPatters).isEqualTo(2);
    }

    @Test
    public void verifyExtractValue(){
        String keyword = dslPatternUtil.getListOfDslKeywords(expression1).get(0);
        assertThat(dslPatternUtil.extractKeyword(keyword)).isEqualTo("loan.interest");
    }

    @Test
    public void verifyKeywordResolverValue(){
        String keyword = dslPatternUtil.getListOfDslKeywords(expression1).get(0);
        assertThat(dslPatternUtil.getKeywordResolver(dslPatternUtil.extractKeyword(keyword))).isEqualTo("loan");
    }

    @Test
    public void verifyKeywordValue(){
        String keyword = dslPatternUtil.getListOfDslKeywords(expression1).get(0);
        assertThat(dslPatternUtil.getKeywordValue(dslPatternUtil.extractKeyword(keyword))).isEqualTo("interest");
    }
}
