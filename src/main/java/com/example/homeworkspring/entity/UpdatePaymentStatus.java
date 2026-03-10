package com.example.homeworkspring.entity;

import lombok.Data;
import lombok.Getter;

import java.util.List;
@Data
public class UpdatePaymentStatus {
    private List<Integer> ticketIds;
    private boolean PaymentStatus;
}
