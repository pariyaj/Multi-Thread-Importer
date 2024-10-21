package com.priyajafari.multithreadproject.Validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ErrorWriter {

    private static final String ERROR_FILE_PATH = "Error.json";
    private final List<CustomError> errorRecords = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Method to add an error
    public void writeError(CustomError customError) {
        errorRecords.add(customError);
    }

    // Method to save all errors to the JSON file
    public void saveErrors() throws Exception {
        if (!errorRecords.isEmpty()) {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enabling pretty printing
            objectMapper.writeValue(new File(ERROR_FILE_PATH), errorRecords);
        }
    }
}
