package me.leantech.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.leantech.server.TestUtils;
import me.leantech.server.datatransferobject.AccountDTO;
import me.leantech.server.datatransferobject.CustomerDTO;
import me.leantech.server.datatransferobject.TransactionDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static me.leantech.server.TestUtils.readTransactionsFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Acceptance test for the requested endpoint methods (Controllers)
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestEndpoints {


    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @DisplayName("Check WebApplicationContext to be initialized correctly and carController is instantiated")
    public void checkContext() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("accountController"));
        Assert.assertNotNull(wac.getBean("customerController"));
        Assert.assertNotNull(wac.getBean("transactionController"));
    }

    @Test
    @DisplayName("Tests account endpoint to return an account by account id input")
    public void testGetAccountByAccountId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/v1/accounts/{accountId}", 1L)
                .header("lean-token", "adf098adsfa098ss")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        objectMapper = new ObjectMapper();
        String content = mvcResult.getResponse().getContentAsString();
        AccountDTO actual = objectMapper.readValue(content, AccountDTO.class);

        Assert.assertEquals(TestUtils.accountDTO, actual);
    }

    @Test
    @DisplayName("Tests customer endpoint to return a customer by customer id input")
    public void testGetCustomerByCustomerId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/v1/customers/{customerId}", 1L)
                .header("lean-token", "adf098adsfa098ss")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String content = mvcResult.getResponse().getContentAsString();
        CustomerDTO actual = objectMapper.readValue(content, CustomerDTO.class);
        assertEquals(TestUtils.customerDTO, actual);

    }

    @Test
    @DisplayName("Tests transaction endpoint to return a transaction by transaction id input")
    public void testGetTransactionByTransactionId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/v1/transactions/{transactionId}", 1L)
                .header("lean-token", "adf098adsfa098ss")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String content = mvcResult.getResponse().getContentAsString();
        TransactionDTO actual = objectMapper.readValue(content, TransactionDTO.class);
        assertEquals(TestUtils.transactionDTO, actual);
    }

    @Test
    @DisplayName("Tests transaction endpoint to return a transaction list by account id input")
    public void testTransactionsListByAccountId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/v1/transactions/accountTransactions")
                .param("accountId", "1")
                .header("lean-token", "adf098adsfa098ss")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String content = mvcResult.getResponse().getContentAsString();
        List<TransactionDTO> actual = objectMapper.readValue(content, objectMapper.getTypeFactory()
                .constructCollectionType(List.class, TransactionDTO.class));
        List<TransactionDTO> transactionDTOS = readTransactionsFromFile();
        List<TransactionDTO> collected = transactionDTOS.stream()
                .filter(transactionDTO -> transactionDTO.getAccountId().equals(1L))
                .sorted(Comparator.comparing(TransactionDTO::getTimestamp).reversed()).collect(Collectors.toList());
        assertEquals(collected, actual);
    }

}
