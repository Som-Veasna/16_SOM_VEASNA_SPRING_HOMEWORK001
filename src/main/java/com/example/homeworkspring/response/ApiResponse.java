package com.example.homeworkspring.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class ApiResponse <T>{
    Boolean success;
    private String message;
    HttpStatus status;
    private T Payload;
    private LocalDateTime timestamp;
}
