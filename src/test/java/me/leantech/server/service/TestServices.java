package me.leantech.server.service;

import com.opencsv.exceptions.CsvException;
import me.leantech.server.TestUtils;
import me.leantech.server.datatransferobject.AccountDTO;
import me.leantech.server.datatransferobject.CustomerDTO;
import me.leantech.server.datatransferobject.TokenDTO;
import me.leantech.server.datatransferobject.TransactionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static me.leantech.server.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for the services provided in the service layer
 */
@SpringBootTest
class TestServices {


    private AccountService accountService;

    private CustomerService customerService;

    private TransactionService transactionService;

    private TokenService tokenService;


    @Autowired
    public TestServices(AccountService accountService, CustomerService customerService,
                        TransactionService transactionService, TokenService tokenService) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.tokenService = tokenService;
    }

    @Test
    @DisplayName("tests if accounts are loaded in the memory")
    public void testAccountsLoaded() throws IOException, CsvException {
        List<AccountDTO> all = accountService.getAll();
        assertEquals(3, all.size());
        List<AccountDTO> accountDTOS = readAccountsFromFile();
        assertEquals(accountDTOS, all);
    }

    @Test
    @DisplayName("tests if customers are loaded in the memory")
    public void testCustomersLoaded() throws IOException, CsvException {
        List<CustomerDTO> all = customerService.getAll();
        assertEquals(3, all.size());
        List<CustomerDTO> customerDTOS = readCustomersFromFile();
        assertEquals(customerDTOS, all);
    }

    @Test
    @DisplayName("tests if tokens are loaded in the memory")
    public void testTokensLoaded() {
        List<TokenDTO> all = tokenService.getAll();
        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("tests if customers are loaded in the memory")
    public void testTransactionsLoaded() throws IOException, CsvException {
        List<TransactionDTO> all = transactionService.getAll();
        assertEquals(7, all.size());
        List<TransactionDTO> transactionDTOS = readTransactionsFromFile();
        assertEquals(transactionDTOS, all);
    }

    @Test
    @DisplayName("tests the returned transactions list for an account id input is descending by timestamp")
    public void testFindByAccountId() throws IOException, CsvException {
        List<TransactionDTO> transactions = transactionService.findByAccountId(1L);
        List<TransactionDTO> transactionDTOS = readTransactionsFromFile();
        List<TransactionDTO> collected = transactionDTOS.stream()
                .filter(transactionDTO -> transactionDTO.getAccountId().equals(1L))
                .sorted(Comparator.comparing(TransactionDTO::getTimestamp).reversed()).collect(Collectors.toList());
        assertEquals(collected, transactions);
        assertTrue(transactions.get(0).getTimestamp().isAfter(transactions.get(1).getTimestamp()));
    }

    @Test
    @DisplayName("tests the returned transaction for an transaction id input")
    public void testFindTransactionByTransactionId() throws IOException, CsvException {
        TransactionDTO actual = transactionService.findById(1L);
        assertEquals(TestUtils.transactionDTO, actual);

    }

    @Test
    @DisplayName("tests the returned account for an account id input")
    public void testFindAccountByAccountId() throws IOException, CsvException {
        AccountDTO actual = accountService.findById(1L);
        assertEquals(TestUtils.accountDTO, actual);
    }

    @Test
    @DisplayName("tests the returned customer for an customer id input")
    public void testFindCustomerByCustomerId() throws IOException, CsvException {
        CustomerDTO actual = customerService.findById(1L);
        assertEquals(TestUtils.customerDTO, actual);
    }
}
