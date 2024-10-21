package com.priyajafari.multithreadproject.batch;

import com.priyajafari.multithreadproject.Validation.*;
import com.priyajafari.multithreadproject.model.Customer;
import org.springframework.batch.item.ItemProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Slf4j
public class CustomerProcessor implements ItemProcessor<Customer,Customer> {

    @Autowired
    private ErrorWriter errorWriter;
    @Autowired
    private CustomerValidationUtil customerValidationUtil;

    @Override
    public Customer process(Customer customer) throws Exception {

        try{
            List<ValidationResult> validationResults = customerValidationUtil.validateCustomerData();
            boolean allValid = validationResults.stream().allMatch(ValidationResult::isValid); //checking if all validations been passed successfully.
            if (allValid)
                return customer;
            else{ 
                //as if we have any validation error 
                List<ValidationResult> results = customerValidationUtil(customer);
                for (ValidationResult result : results)
                    if (!result.isValid())
                        errorWriter.writeError(new CustomError("AccountsError.csv", customer.getRecordNumber(), result.getError()));
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
