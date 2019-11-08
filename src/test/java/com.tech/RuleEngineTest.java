package com.tech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.knowledgeBase.KnowledgeBaseService;
import com.tech.knowledgeBase.models.Rule;
import com.tech.restAPI.RuleNamespace;
import com.tech.rulesImpl.carLoanRuleEngine.LoanDetailsOutputResult;
import com.tech.rulesImpl.carLoanRuleEngine.UserInfoInputData;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RuleEngineTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private KnowledgeBaseService knowledgeBaseServiceMock;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.objectMapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(knowledgeBaseServiceMock.getAllRuleByNamespace(Mockito.any())).thenReturn(getListOfRules());
    }

    @Test
    public void verifyGetAllRules() throws Exception {
        mockMvc.perform(get("/get-all-rules")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
        );
    }

    @Test
    public void verifyPostCarLoanRuleOne() throws Exception {
        UserInfoInputData userInfoInputData = UserInfoInputData.builder()
                .firstName("Ramesh")
                .accountNumber(1234567L)
                .requestedLoanAmount(100000.0)
                .salary(80000.0)
                .creditScore(900)
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/car-loan")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userInfoInputData)))
                .andExpect(status().isOk()
                ).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        log.info("Output Response: {}", actualResponseBody);

        LoanDetailsOutputResult loanDetailsOutputResult = LoanDetailsOutputResult.builder()
                .approvalStatus(true)
                .interestRate(9.0f)
                .sanctionedAmount(90100.0)
                .build();
        String expectedResponseBody = objectMapper.writeValueAsString(loanDetailsOutputResult);

        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void verifyPostCarLoanRuleTwo() throws Exception {
        UserInfoInputData userInfoInputData = UserInfoInputData.builder()
                .firstName("Ramesh")
                .accountNumber(1234567L)
                .requestedLoanAmount(100000.0)
                .salary(80000.0)
                .creditScore(400)
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/car-loan")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userInfoInputData)))
                .andExpect(status().isOk()
                ).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        log.info("Output Response: {}", actualResponseBody);

        LoanDetailsOutputResult loanDetailsOutputResult = LoanDetailsOutputResult.builder()
                .approvalStatus(true)
                .interestRate(9.0f)
                .sanctionedAmount(90500.0)
                .build();
        String expectedResponseBody = objectMapper.writeValueAsString(loanDetailsOutputResult);

        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    private List<Rule> getListOfRules(){
        log.info("Returning Mock Data!");
        Rule rule1 = Rule.builder()
                .ruleNamespace(RuleNamespace.CAR_LOAN)
                .ruleId("1")
                .condition("input.salary >= 70000 && input.creditScore >= 900 && $(loan.interest) >= 8")
                .action("output.setApprovalStatus(true); output.setSanctionedAmount((input.requestedLoanAmount * 0.9)+100.0);output.setInterestRate($(loan.interest));")
                .priority(1)
                .description("A person is eligible for car loan?")
                .build();
        Rule rule2 = Rule.builder()
                .ruleNamespace(RuleNamespace.CAR_LOAN)
                .ruleId("2")
                .condition("input.salary >= 20000 && input.creditScore <= 500 && $(loan.interest) >= 8")
                .action("output.setApprovalStatus(true); output.setSanctionedAmount((input.requestedLoanAmount * 0.9)+500.0);output.setInterestRate($(loan.interest));")
                .priority(1)
                .description("A person is eligible for car loan?")
                .build();

        List<Rule> allRulesByNamespace = Lists.newArrayList(rule1, rule2);
        return allRulesByNamespace;
    }
}
