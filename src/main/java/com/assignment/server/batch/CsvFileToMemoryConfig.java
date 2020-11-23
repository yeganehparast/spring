package com.assignment.server.batch;

import com.assignment.server.datatransferobject.AccountDTO;
import com.assignment.server.datatransferobject.CustomerDTO;
import com.assignment.server.datatransferobject.TokenDTO;
import com.assignment.server.datatransferobject.TransactionDTO;
import com.assignment.server.service.AccountService;
import com.assignment.server.service.CustomerService;
import com.assignment.server.service.TokenService;
import com.assignment.server.service.TransactionService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.DataBinder;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class reads the CSV files as well as text files and loads the contents of them into memory.
 */
@EnableBatchProcessing
@Configuration
public class CsvFileToMemoryConfig {


    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private AccountService accountService;

    private CustomerService customerService;

    private TransactionService transactionService;

    private TokenService tokenService;

    @Autowired
    public CsvFileToMemoryConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                                 AccountService accountService, CustomerService customerService,
                                 TransactionService transactionService, TokenService tokenService) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.accountService = accountService;
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.tokenService = tokenService;
    }

    @Bean
    public FlatFileItemReader<AccountDTO> accountReader() {
        FlatFileItemReader<AccountDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("account.csv"));
        reader.setLineMapper(new DefaultLineMapper<AccountDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"accountId", "customerId", "accountType", "accountNumber", "ibanCode", "accountStatus", "balance", "currencyCode"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<AccountDTO>() {{
                setTargetType(AccountDTO.class);
            }});
        }});
        return reader;
    }

    @Bean
    public FlatFileItemReader<CustomerDTO> customerReader() {
        FlatFileItemReader<CustomerDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("customer.csv"));
        reader.setLineMapper(new DefaultLineMapper<CustomerDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"customerId", "name", "dateOfBirth", "nationality", "emailAddress", "address"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<CustomerDTO>() {
                @Override
                protected void initBinder(DataBinder binder) {
                    binder.registerCustomEditor(LocalDate.class, new CustomLocalDatePropertyEditorSupport());
                }

                {
                    setTargetType(CustomerDTO.class);
                }
            });

        }});
        return reader;
    }

    @Bean
    public FlatFileItemReader<TransactionDTO> transactionReader() {
        FlatFileItemReader<TransactionDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("transaction.csv"));
        reader.setLineMapper(new DefaultLineMapper<TransactionDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"transcationId", "accountId", "transactionType", "description", "amount", "currencyCode", "timestamp"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<TransactionDTO>() {
                @Override
                protected void initBinder(DataBinder binder) {
                    binder.registerCustomEditor(LocalDateTime.class, new CustomLocalDateTimePropertyEditorSupport());
                }

                {
                    setTargetType(TransactionDTO.class);
                }
            });
        }});
        return reader;
    }

    @Bean
    public FlatFileItemReader<TokenDTO> tokenReader() {
        FlatFileItemReader<TokenDTO> reader = new FlatFileItemReader<TokenDTO>();
        reader.setResource(new ClassPathResource("whitelist.txt"));
        reader.setLineMapper(new DefaultLineMapper<TokenDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("token");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<TokenDTO>() {{
                setTargetType(TokenDTO.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemWriter<AccountDTO> accountWriter() throws Exception {
        return list -> list.forEach(account -> accountService.save(account));
    }

    @Bean
    public ItemWriter<CustomerDTO> customerWriter() throws Exception {
        return list -> list.forEach(customer -> customerService.save(customer));
    }

    @Bean
    public ItemWriter<TokenDTO> tokenWriter() throws Exception {
        return list -> list.forEach(token -> tokenService.save(token));
    }

    @Bean
    public ItemWriter<TransactionDTO> transactionWriter() throws Exception {
        return list -> list.forEach(transaction -> transactionService.save(transaction));
    }


    @Bean
    public Step accountCsvFileToDatabaseStep() throws Exception {
        return stepBuilderFactory.get("accountCsvToMemoryStep")
                .<AccountDTO, AccountDTO>chunk(1)
                .reader(accountReader())
                .writer(accountWriter())
                .build();
    }

    @Bean
    public Step customerCsvFileToDatabaseStep() throws Exception {
        return stepBuilderFactory.get("customerCsvToMemoryStep")
                .<CustomerDTO, CustomerDTO>chunk(1)
                .reader(customerReader())
                .writer(customerWriter())
                .build();
    }

    @Bean
    public Step tokenCsvFileToDatabaseStep() throws Exception {
        return stepBuilderFactory.get("tokenCsvToMemoryStep")
                .<TokenDTO, TokenDTO>chunk(1)
                .reader(tokenReader())
                .writer(tokenWriter())
                .build();
    }

    @Bean
    public Step transactionCsvFileToDatabaseStep() throws Exception {
        return stepBuilderFactory.get("transactionCsvToMemoryStep")
                .<TransactionDTO, TransactionDTO>chunk(1)
                .reader(transactionReader())
                .writer(transactionWriter())
                .build();
    }

    @Bean
    Job csvFileToDatabaseJob(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory.get("JOB")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(accountCsvFileToDatabaseStep())
                .next(customerCsvFileToDatabaseStep())
                .next(transactionCsvFileToDatabaseStep())
                .next(tokenCsvFileToDatabaseStep())
                .build();
    }

    private class CustomLocalDatePropertyEditorSupport extends PropertyEditorSupport {

        @Override
        public void setAsText(String text) {
            if (!text.equals("")) {
                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            } else {
                setValue(null);
            }
        }

        @Override
        public String getAsText() {
            Object value = getValue();
            if (value != null) {
                return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((LocalDate) value);
            } else {
                return "";
            }
        }
    }

    private class CustomLocalDateTimePropertyEditorSupport extends PropertyEditorSupport {

        @Override
        public void setAsText(String text) {
            if (!text.equals("")) {
                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s")));
            } else {
                setValue(null);
            }
        }

        @Override
        public String getAsText() {
            Object value = getValue();
            if (value != null) {
                return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format((LocalDateTime) value);
            } else {
                return "";
            }
        }
    }
}
