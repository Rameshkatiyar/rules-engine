package com.tech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.knowledgeBase.KnowledgeBaseService;
import com.tech.knowledgeBase.models.Rule;
import com.tech.restAPI.RuleNamespace;
import com.tech.rulesImpl.insuranceRuleEngine.InsuranceDetails;
import com.tech.rulesImpl.insuranceRuleEngine.PolicyHolderDetails;
import com.tech.rulesImpl.loanRuleEngine.LoanDetails;
import com.tech.rulesImpl.loanRuleEngine.UserDetails;
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
        UserDetails userDetails = UserDetails.builder()
                .firstName("Mark")
                .lastName("K")
                .accountNumber(1234567L)
                .requestedLoanAmount(1000000.0)
                .monthlySalary(50000.0)
                .cibilScore(600)
                .age(25)
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/loan")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDetails)))
                .andExpect(status().isOk()
                ).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        LoanDetails loanDetails = LoanDetails.builder()
                .approvalStatus(true)
                .interestRate(9.0f)
                .sanctionedPercentage(90f)
                .accountNumber(1234567L)
                .processingFees(2000.0)
                .build();
        String expectedResponseBody = objectMapper.writeValueAsString(loanDetails);

        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void verifyPostCarLoanRuleTwo() throws Exception {
        UserDetails userDetails = UserDetails.builder()
                .firstName("Jhone")
                .lastName("L")
                .accountNumber(1234567L)
                .requestedLoanAmount(800000.0)
                .monthlySalary(30000.0)
                .cibilScore(400)
                .age(25)
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/loan")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDetails)))
                .andExpect(status().isOk()
                ).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        LoanDetails loanDetails = LoanDetails.builder()
                .approvalStatus(true)
                .interestRate(9.0f)
                .sanctionedPercentage(70f)
                .accountNumber(1234567L)
                .processingFees(1000.0)
                .build();
        String expectedResponseBody = objectMapper.writeValueAsString(loanDetails);

        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    private List<Rule> getListOfRules(){
        Rule rule1 = Rule.builder()
                .ruleNamespace(RuleNamespace.LOAN)
                .ruleId("1")
                .condition("input.monthlySalary >= 50000.0 && input.cibilScore >= 500 && input.requestedLoanAmount<1500000 && $(bank.target_done) == false")
                .action("output.setApprovalStatus(true); output.setInterestRate($(bank.interest)); output.setSanctionedPercentage(90);output.setProcessingFees(2000);output.setAccountNumber(input.accountNumber);")
                .priority(1)
                .description("A person is eligible for loan?")
                .build();
        Rule rule2 = Rule.builder()
                .ruleNamespace(RuleNamespace.LOAN)
                .ruleId("2")
                .condition("(input.monthlySalary < 50000.0 && input.cibilScore <= 300 && input.requestedLoanAmount >= 1000000) || $(bank.target_done) == true")
                .action("output.setApprovalStatus(false); output.setInterestRate(0.0); output.setSanctionedPercentage(0.0);output.setProcessingFees(0);output.setAccountNumber(input.accountNumber);")
                .priority(2)
                .description("A person is eligible for car loan?")
                .build();
        Rule rule3 = Rule.builder()
                .ruleNamespace(RuleNamespace.LOAN)
                .ruleId("3")
                .condition("input.monthlySalary >= 20000.0 && input.cibilScore >= 300 && input.cibilScore < 500 && input.requestedLoanAmount <= 1000000 && $(bank.target_done) == false")
                .action("output.setApprovalStatus(true); output.setInterestRate($(bank.interest)); output.setSanctionedPercentage(70);output.setProcessingFees(1000);output.setAccountNumber(input.accountNumber);")
                .priority(2)
                .description("A person is eligible for car loan?")
                .build();

        List<Rule> allRulesByNamespace = Lists.newArrayList(rule1, rule2, rule3);
        return allRulesByNamespace;
    }
}
