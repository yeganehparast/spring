package me.leantech.server.dao;

import me.leantech.server.TestUtils;
import me.leantech.server.dataaccessobject.AccountDAO;
import me.leantech.server.dataaccessobject.CustomerDAO;
import me.leantech.server.dataaccessobject.TransactionDAO;
import me.leantech.server.datatransferobject.AccountDTO;
import me.leantech.server.datatransferobject.CustomerDTO;
import me.leantech.server.datatransferobject.TransactionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This test unit mocks DAO classes and checks the result to be correct
 */

@ExtendWith(MockitoExtension.class)
public class DAOTest {
    @Mock
    private CustomerDAO customerDAO;
    @Mock
    private AccountDAO accountDAO;
    @Mock
    private TransactionDAO transactionDAO;

    @Test
    @DisplayName("Finds an account by account Id")
    public void findAccountByAccountId() throws Exception {
        //Given
        Long accountId = 1L;
        Mockito.lenient()
                .doReturn(TestUtils.accountDTO)
                .when(accountDAO)
                .findByAccountId(accountId);
        //When
        AccountDTO accountWhen = accountDAO.findByAccountId(accountId);


        //Then expect
        assertNotNull(accountWhen);
        assertNotNull(accountWhen.getAccountId());
        assertNotNull(accountWhen.getAccountNumber());
    }

    @Test
    @DisplayName("Finds a transaction by transaction Id")
    public void findTransactionByTransactionId() throws Exception {
        //Given
        Long transactionId = 1L;
        Mockito.lenient()
                .doReturn(TestUtils.transactionDTO)
                .when(transactionDAO)
                .findByTransactionId(transactionId);
        //When
        TransactionDTO transactionWhen = transactionDAO.findByTransactionId(transactionId);


        //Then expect
        assertNotNull(transactionWhen);
        assertNotNull(transactionWhen.getTransactionId());
        assertNotNull(transactionWhen.getAccountId());
        assertNotNull(transactionWhen.getAmount());
    }

    @Test
    @DisplayName("Finds a customer by customer Id")
    public void find() throws Exception {
        //Given
        Long customerId = 1L;
        Mockito.lenient()
                .doReturn(TestUtils.customerDTO)
                .when(customerDAO)
                .findByCustomerId(customerId);
        //When
        CustomerDTO customerWhen = customerDAO.findByCustomerId(customerId);


        //Then expect
        assertNotNull(customerWhen);
        assertNotNull(customerWhen.getCustomerId());
        assertNotNull(customerWhen.getName());
        assertNotNull(customerWhen.getAddress());
    }
}
