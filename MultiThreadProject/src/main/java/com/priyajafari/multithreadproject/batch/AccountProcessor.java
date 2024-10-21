package com.priyajafari.multithreadproject.batch;

import com.priyajafari.multithreadproject.Validation.*;
import com.priyajafari.multithreadproject.model.Account;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


public class AccountProcessor implements ItemProcessor<Account,Account> {

    @Autowired
    private ErrorWriter errorWriter;
    @Autowired
    private AccountValidationUtil accountValidationUtil;

    @Override
    public Account process(Account account) throws Exception {

        try{
            List<ValidationResult> validationResults = accountValidationUtil.validateAccountData();
            boolean allValid = validationResults.stream().allMatch(ValidationResult::isValid);
            if (allValid)
                return account; //returning valid account
            else{
                List<ValidationError> validationErrors = accountValidationUtil.accountValidationErrorFinder();
                for (ValidationError validationError : validationErrors ) {
                    errorWriter.writeError(new CustomError("AccountsError.csv", account.getRecordNumber(), validationError));
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
