package com.priyajafari.multithreadproject.batch;

import com.priyajafari.multithreadproject.Validation.ErrorWriter;
import com.priyajafari.multithreadproject.model.Customer;
import com.priyajafari.multithreadproject.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class CustomerWriter implements ItemWriter<Customer> {

    private final CustomerRepository customerRepository;
    private final ErrorWriter errorWriter;

    public CustomerWriter(CustomerRepository customerRepository, ErrorWriter errorWriter) {
        this.customerRepository = customerRepository;
        this.errorWriter = errorWriter;
    }

    public void write(Chunk<? extends Customer> chunk) throws Exception {
        log.info("Adding data : {}", chunk.getItems().size());

        customerRepository.saveAll(chunk.getItems());
        errorWriter.saveErrors();
    }
}
