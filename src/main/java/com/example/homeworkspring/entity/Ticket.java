package com.example.homeworkspring.entity;

import lombok.Data;

@Data
public class Ticket {
    private int ticketId;
    private String passengerName;
    private String travelDate;
    private String sourceStation;
    private String destinationStation;
    private double price;
    private Boolean paymentStatus;
    private String ticketStatus;
    private String seatNumber;
    private static int countId = 0;
    public Ticket(String passengerName, String travelDate, String sourceStation, String destinationStation, double price, Boolean paymentStatus,String ticketStatus,String seatNumber) {
        ++countId;
        ticketId=countId;
        this.passengerName = passengerName;
        this.travelDate = travelDate;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.ticketStatus = ticketStatus;
        this.seatNumber = seatNumber;
    }

}
