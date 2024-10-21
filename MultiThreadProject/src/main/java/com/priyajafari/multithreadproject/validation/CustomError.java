package com.priyajafari.multithreadproject.Validation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomError {

    private Long id;

    @NotNull
    private String fileName;
    @NotNull
    private int recordNumber;
    @NotNull
    private String errorCode;
    @NotNull
    private String errorClassificationName;
    @NotNull
    private String errorDescription;
    @NotNull
    private Date errorDate;

    public CustomError(String fileName, int recordNumber, ValidationError error) {
        this.fileName = fileName;
        this.recordNumber = recordNumber;
        this.errorCode= error.getErrorCode();
        this.errorClassificationName = error.getClassificationName();
        this.errorDescription = error.getErrorDescription();
        this.errorDate = new Date();
    }

}
