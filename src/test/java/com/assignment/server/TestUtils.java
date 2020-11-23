package com.assignment.server;

import com.assignment.server.datatransferobject.AccountDTO;
import com.assignment.server.datatransferobject.CustomerDTO;
import com.assignment.server.datatransferobject.TransactionDTO;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {


    public static AccountDTO accountDTO = AccountDTO.builder()
            .accountId(1L)
            .customerId(3L)
            .balance(new BigDecimal("123.56"))
            .currencyCode("EUR")
            .accountStatus("ACTIVE")
            .accountType("Savings")
            .ibanCode("DE000000789654123051")
            .accountNumber(789654123051L)
            .build();
    public static CustomerDTO customerDTO = CustomerDTO.builder()
            .customerId(1L)
            .name("Joe Steinmeir")
            .nationality("GERMAN")
            .address("1 street, city, country")
            .emailAddress("user1@mycompamny.com")
            .dateOfBirth(LocalDate.parse("1990-09-15", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .build();
    public static TransactionDTO transactionDTO = TransactionDTO.builder()
            .accountId(1L)
            .amount(new BigDecimal("33.256"))
            .currencyCode("GBP")
            .description("Transfer from Savings Account")
            .timestamp(LocalDateTime.parse("2020-05-01 11:37:45", DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s")))
            .transactionId(1L)
            .transactionType("Transfer")
            .build();

    public static List<AccountDTO> readAccountsFromFile() throws IOException, CsvException {
        Path resourceDirectory = Paths.get("src", "main", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath() + "/" + "account.csv";
        FileReader fileReader = new FileReader(absolutePath);
        final List<AccountDTO> accountDTOS = new ArrayList<>();
        CSVReader reader = new CSVReader(fileReader);
        List<String[]> parsedList = reader.readAll();
        parsedList.forEach(split -> {
            AccountDTO accountDTO = new AccountDTO(Long.valueOf(split[0]), Long.valueOf(split[1]), split[2],
                    Long.valueOf(split[3]), split[4], split[5], new BigDecimal(split[6]), split[7]);
            accountDTOS.add(accountDTO);
        });
        return accountDTOS;
    }

    public static List<CustomerDTO> readCustomersFromFile() throws IOException, CsvException {
        Path resourceDirectory = Paths.get("src", "main", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath() + "/" + "customer.csv";
        FileReader fileReader = new FileReader(absolutePath);
        CSVReader reader = new CSVReader(fileReader);
        List<String[]> parsedList = reader.readAll();
        final List<CustomerDTO> customerDTOS = new ArrayList<>();
        parsedList.forEach(split -> {
            CustomerDTO customerDTO = new CustomerDTO(Long.valueOf(split[0]), split[1], LocalDate.parse(split[2],
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")), split[3], split[4], split[5]);
            customerDTOS.add(customerDTO);
        });
        return customerDTOS;
    }

    public static List<TransactionDTO> readTransactionsFromFile() throws IOException, CsvException {
        Path resourceDirectory = Paths.get("src", "main", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath() + "/" + "transaction.csv";
        FileReader fileReader = new FileReader(absolutePath);
        CSVReader reader = new CSVReader(fileReader);
        List<String[]> parsedList = reader.readAll();
        final List<TransactionDTO> transactionDTOS = new ArrayList<>();
        parsedList.forEach(split -> {
            TransactionDTO transactionDTO = new TransactionDTO(Long.valueOf(split[0]), Long.valueOf(split[1]),
                    split[2], split[3], new BigDecimal(split[4]), split[5],
                    LocalDateTime.parse(split[6], DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s")));
            transactionDTOS.add(transactionDTO);
        });
        return transactionDTOS;
    }

}
