package com.priyajafari.multithreadproject.config;

import com.priyajafari.multithreadproject.Validation.CustomerValidationUtil;
import com.priyajafari.multithreadproject.Validation.ErrorWriter;
import com.priyajafari.multithreadproject.batch.AccountProcessor;
import com.priyajafari.multithreadproject.batch.AccountWriter;
import com.priyajafari.multithreadproject.batch.CustomerProcessor;
import com.priyajafari.multithreadproject.batch.CustomerWriter;
import com.priyajafari.multithreadproject.model.Account;
import com.priyajafari.multithreadproject.model.AccountType;
import com.priyajafari.multithreadproject.model.Customer;
import com.priyajafari.multithreadproject.repository.AccountRepository;
import com.priyajafari.multithreadproject.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class BatchConfig {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ErrorWriter errorWriter;
    @Autowired
    @Lazy
    private AccountProcessor accountProcessor;


    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager, CustomerRepository customerRepository, CustomerProcessor customerProcessor, ErrorWriter errorWriter){

        return new JobBuilder("csv-reading-job",jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(customerStep(jobRepository,transactionManager,customerRepository,customerProcessor,errorWriter))
                .next(accountStep(jobRepository,transactionManager,accountRepository,accountProcessor,errorWriter))
                .end()
                .build();
    }

    @Bean
    public Step customerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, CustomerRepository customerRepository, CustomerProcessor customerProcessor, ErrorWriter errorWriter){
        return new StepBuilder("customer-step", jobRepository)
                .<Customer,Customer>chunk(10,transactionManager)
                .reader(customerFlatFileItemReader())
                .processor(customerProcessor())
                .writer(customerWriter(customerRepository,errorWriter))
                .build();
    }

    @Bean
    public FlatFileItemReader<Customer> customerFlatFileItemReader(){
        return new FlatFileItemReaderBuilder<Customer>()
                .resource(new ClassPathResource("Customers.csv"))
                .name("Customer Item Reader")
                .delimited()
                .names( new String[] {"recordNumber" , "customerId" , "customerName" , "customerSurName" , "customerAddress" , "customerZipcode" , "customerNationalId", "customerBirthDate"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Customer.class);
                }})
                .build();
    }

    @Bean
    public CustomerProcessor customerProcessor(){
        return new CustomerProcessor();
    }

    @Bean
    @StepScope
    public ItemProcessor<Customer, Customer> customerProcessor(AccountProcessor accountProcessor){
        CompositeItemProcessor<Customer,Customer> processor = new CompositeItemProcessor<>();
        processor.setDelegates(List.of(customerProcessor()));
        return processor;
    }

    @Bean
    @StepScope
    public ItemWriter<Customer> customerWriter(CustomerRepository customerRepository, ErrorWriter errorWriter) {
        return new CustomerWriter(customerRepository, errorWriter);
    }

    @Bean
    public Step accountStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, AccountRepository accountRepository, AccountProcessor accountProcessor, ErrorWriter errorWriter){
        return new StepBuilder("account-step",jobRepository)
                .<Account,Account>chunk(10,transactionManager)
                .reader(accountFlatFileItemReader())
                .processor(accountProcessor)
                .writer(accountWriter(accountRepository,errorWriter))
                .build();
    }

    @Bean
    public FlatFileItemReader<Account> accountFlatFileItemReader(){
        return new FlatFileItemReaderBuilder<Account>()
                .resource(new ClassPathResource("Accounts.csv"))
                .name("Account Item Reader")
                .delimited()
                .names(new String[]{"recordNumber" , "accountNumber" , "accountType" , "accountCustomerId" , "accountLimit" , "accountOpenDate" , "accountBalance"} )
                .fieldSetMapper(fieldSet -> {
                    BeanWrapperFieldSetMapper<Account> mapper = new BeanWrapperFieldSetMapper<>();
                    mapper.setTargetType(Account.class);
                    Account account = mapper.mapFieldSet(fieldSet);
                    // Manually setting the custom field AccountType
                    account.setAccountType(AccountType.valueOf(fieldSet.readString("accountType")));
                    return account;
                })
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Account, Account> processor() {
        CompositeItemProcessor<Account, Account> processor = new CompositeItemProcessor<>();
        processor.setDelegates(List.of(accountProcessor));
        return processor;
    }

    @Bean
    @StepScope
    public AccountProcessor accountProcessorBean() {
        return new AccountProcessor();
    }
    @Bean
    @StepScope
    public ItemWriter<Account> accountWriter(AccountRepository accountRepository, ErrorWriter errorWriter) {
        return new AccountWriter(accountRepository, errorWriter);
    }
}
