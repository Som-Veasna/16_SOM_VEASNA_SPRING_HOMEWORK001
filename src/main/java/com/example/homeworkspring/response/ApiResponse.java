package com.example.homeworkspring.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class ApiResponse <T>{
    Boolean success;
    private String message;
    HttpStatus status;
    private LocalDateTime timestamp;
    private T Payroad;

}
