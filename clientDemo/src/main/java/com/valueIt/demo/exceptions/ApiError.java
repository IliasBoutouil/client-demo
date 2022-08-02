package com.valueIt.demo.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp=LocalDateTime.now();
    private String message;
    private String debugMessage;

    public ApiError(HttpStatus status, String message,Throwable exception) {
        this.status = status;
        this.message = message;
        this.debugMessage=exception.getLocalizedMessage();
    }
}
