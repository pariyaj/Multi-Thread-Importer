package com.priyajafari.multithreadproject.batch;

import com.priyajafari.multithreadproject.Validation.ErrorWriter;
import com.priyajafari.multithreadproject.model.Account;
import com.priyajafari.multithreadproject.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
public class AccountWriter implements ItemWriter<Account> {

    private final AccountRepository accountRepository;
    private final ErrorWriter errorWriter;
    
    public AccountWriter(AccountRepository accountRepository, ErrorWriter errorWriter) {
        this.accountRepository = accountRepository;
        this.errorWriter = errorWriter;
    }

    public void write(Chunk<? extends Account> chunk) throws Exception {
        log.info("Writing : {}", chunk.getItems().size());
        accountRepository.saveAll(chunk.getItems());
        errorWriter.saveErrors();
    }
}
