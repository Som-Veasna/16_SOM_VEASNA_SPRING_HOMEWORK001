package com.example.homeworkspring.controller;

import com.example.homeworkspring.entity.Ticket;
import com.example.homeworkspring.entity.TicketRequest;
import com.example.homeworkspring.entity.TicketStatus;
import com.example.homeworkspring.entity.UpdatePaymentStatus;
import com.example.homeworkspring.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {
    List<Ticket> tickets=new ArrayList<>();
    public TicketController() {
        tickets.add(new Ticket("Kok dara", "20-02-2022", "PP", "TK", 200d, true, "BOOKED", "100B")
        );
        tickets.add(new Ticket("Moni Visa","12-04-2025","Takeo","Phnom Penh",300d,true,"BOOKED","200D"));
    }
    @Operation(summary = "Get all ticket")
    @GetMapping
    public ResponseEntity<?> getTickets() {
        ApiResponse<List<Ticket>> response=new ApiResponse<>(
                true,"Succesfully",HttpStatus.OK,LocalDateTime.now(),tickets
        );
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(summary = "Add ticket")
    @PostMapping
    public ResponseEntity<?> addTicket(@RequestBody TicketRequest ticket) {
        Ticket ticket1=new Ticket(ticket.getPassengerName(),ticket.getTravelDate(),ticket.getSourceStation(),ticket.getDestinationStation(),ticket.getPrice(),ticket.getPaymentStatus(),ticket.getTicketStatus().toUpperCase(),ticket.getSeatNumber());
        tickets.add(ticket1);
        ApiResponse<Ticket> response=new ApiResponse<>(true,"Succesfully",HttpStatus.OK,LocalDateTime.now(),ticket1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(summary = "Get ticket by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable("id") int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId()==id) {
                ApiResponse<Ticket> response=new ApiResponse<>(true,"Succesfully",HttpStatus.OK,LocalDateTime.now(),ticket);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Get ticket by name")
    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam String name) {
          for (Ticket ticket : tickets) {
              if (ticket.getPassengerName().equals(name)) {
                  ApiResponse<Ticket> response=new ApiResponse<>(true,"Succesfully",HttpStatus.OK,LocalDateTime.now(),ticket);
                  return new ResponseEntity<>(response, HttpStatus.OK);
              }
          }
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Get ticket by status and Travel date")
    @GetMapping("/filter")
    public ResponseEntity<?> FiterTicketByStatusAndTravelDate(@RequestParam TicketStatus ticketStatus, @RequestParam String travelDate) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketStatus().equals(ticketStatus.toString())&&ticket.getTravelDate().equalsIgnoreCase(ticket.getTravelDate())) {
                ApiResponse<Ticket> response=new ApiResponse<>(true,"Succesfully",HttpStatus.OK,LocalDateTime.now(),ticket);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
       return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
    @Operation(summary = "Add multiple ticket")
    @PostMapping("/bulk")
    public ResponseEntity<?> addMultipleTickets(@RequestBody List<TicketRequest> ticket) {
        for (TicketRequest ticket1 : ticket) {
            tickets.add(
                    new Ticket(ticket1.getPassengerName(),ticket1.getTravelDate(),ticket1.getSourceStation(),ticket1.getDestinationStation(),ticket1.getPrice(),ticket1.getPaymentStatus(),ticket1.getTicketStatus(),ticket1.getSeatNumber())
            );
            ApiResponse<List<Ticket>> response=new ApiResponse<>(true,"Succesfully",HttpStatus.OK,LocalDateTime.now(),tickets);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
    @Operation(summary = "Delete ticket by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicketById(@PathVariable int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId()==id) {
                tickets.remove(ticket);
                ApiResponse<Ticket> response=new ApiResponse<>(true,"Succesfully",HttpStatus.OK,LocalDateTime.now(),ticket);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Update ticket by id")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicketById(@PathVariable int id, @RequestBody TicketRequest ticket) {
        for(Ticket ticket1: tickets) {
            if (ticket1.getTicketId()==id) {
                ticket1.setPassengerName(ticket.getPassengerName());
                ticket1.setTravelDate(ticket.getTravelDate());
                ticket1.setSourceStation(ticket.getSourceStation());
                ticket1.setDestinationStation(ticket.getDestinationStation());
                ticket1.setPrice(ticket.getPrice());
                ticket1.setPaymentStatus(ticket.getPaymentStatus());
                ticket1.setTicketStatus(ticket.getTicketStatus());
                ticket1.setSeatNumber(ticket.getSeatNumber());
                ApiResponse<Ticket> response =new ApiResponse<>(true,"Succesfully",HttpStatus.OK,LocalDateTime.now(),ticket1);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Update status by multiple ticket")
    @PutMapping("/bulk")
    public ResponseEntity<?> updateMultiplePaaymentStatus(@RequestBody UpdatePaymentStatus updatePaymentStatus) {
        List<Ticket> ticketUpdate=new ArrayList<>();
        for(Integer id:updatePaymentStatus.getTicketIds()) {
            for (Ticket ticket : tickets) {
                if (ticket.getTicketId()==id) {
                    ticket.setPaymentStatus(updatePaymentStatus.isPaymentStatus());
                     ticketUpdate.add(ticket);
                }
            }
        }
        ApiResponse<List<Ticket>> response=new ApiResponse<>(true,"Succesfully",HttpStatus.OK,LocalDateTime.now(),tickets);
       return new ResponseEntity<>(response, HttpStatus.OK);

    }




}
