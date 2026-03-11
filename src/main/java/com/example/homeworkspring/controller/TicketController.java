package com.example.homeworkspring.controller;

import com.example.homeworkspring.entity.Ticket;
import com.example.homeworkspring.entity.TicketRequest;
import com.example.homeworkspring.entity.TicketStatus;
import com.example.homeworkspring.entity.UpdatePaymentStatus;
import com.example.homeworkspring.response.ApiResponse;
import com.example.homeworkspring.response.NotFoundResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
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
                true,"Succesfully",HttpStatus.OK,tickets,LocalDateTime.now()
        );
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(summary = "Add ticket")
    @PostMapping
    public ResponseEntity<?> addTicket(@RequestBody TicketRequest ticket) {
        Ticket ticket1=new Ticket(ticket.getPassengerName(),ticket.getTravelDate(),ticket.getSourceStation(),ticket.getDestinationStation(),ticket.getPrice(),ticket.getPaymentStatus(),ticket.getTicketStatus().toUpperCase(),ticket.getSeatNumber());
        tickets.add(ticket1);
        ApiResponse<Ticket> response=new ApiResponse<>(true,"Succesfully",HttpStatus.OK,ticket1,LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @Operation(summary = "Get ticket by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable("id") int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId()==id) {
                ApiResponse<Ticket> response=new ApiResponse<>(true,"Succesfully",HttpStatus.OK,ticket,LocalDateTime.now());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        NotFoundResponse notFoundResponse=new NotFoundResponse(false,"No Ticket found with Id:"+id,HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(notFoundResponse, HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Get ticket by name")
    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam String name) {
        String message;
        List<Ticket> isTicketFound=new ArrayList<>();
          for (Ticket ticket : tickets) {
              if (ticket.getPassengerName().equals(name)) {
                  isTicketFound.add(ticket);
              }
          }
          if(isTicketFound.isEmpty()){
              message="No Ticket found with name:"+name;
          }else {
              message="Ticket found with name:"+name;
          }
        ApiResponse<List<Ticket>> response=new ApiResponse<>(true,message,HttpStatus.OK,isTicketFound,LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(summary = "Get ticket by status and Travel date")
    @GetMapping("/filter")
    public ResponseEntity<?> fiterTicketByStatusAndTravelDate(@RequestParam TicketStatus ticketStatus, @RequestParam String travelDate) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketStatus().equals(ticketStatus.toString())&&ticket.getTravelDate().equals(travelDate)) {
                ApiResponse<Ticket> response=new ApiResponse<>(true,"Succesfully",HttpStatus.OK,ticket,LocalDateTime.now());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        NotFoundResponse notFoundResponse=new NotFoundResponse(false,"Ticket status and Travel date not found",HttpStatus.NOT_FOUND, LocalDateTime.now());
       return new ResponseEntity<>(notFoundResponse, HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Add multiple ticket")
    @PostMapping("/bulk")
    public ResponseEntity<?> addMultipleTickets(@RequestBody List<TicketRequest> ticket) {
        List<Ticket> newTickets=new ArrayList<>();
        List<Ticket> addTickets = new ArrayList<>();
        for (TicketRequest ticket1 : ticket) {
            Ticket multipleTicket= new Ticket(ticket1.getPassengerName(), ticket1.getTravelDate(), ticket1.getSourceStation(), ticket1.getDestinationStation(), ticket1.getPrice(), ticket1.getPaymentStatus(), ticket1.getTicketStatus(), ticket1.getSeatNumber());
            tickets.add(multipleTicket);
            addTickets.add(multipleTicket);
            ApiResponse<List<Ticket>> response = new ApiResponse<>(true, "Succesfully", HttpStatus.OK,newTickets, LocalDateTime.now());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }
    @Operation(summary = "Delete ticket by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicketById(@PathVariable int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId()==id) {
                tickets.remove(ticket);
                ApiResponse<Ticket> response=new ApiResponse<>(true,"Succesfully",HttpStatus.OK,null,LocalDateTime.now());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        NotFoundResponse notFoundResponse=new NotFoundResponse(false,"No Ticket found with Id:"+id,HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(notFoundResponse, HttpStatus.NOT_FOUND);
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
                ApiResponse<Ticket> response =new ApiResponse<>(true,"Succesfully",HttpStatus.OK,ticket1,LocalDateTime.now());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        NotFoundResponse notFoundResponse=new NotFoundResponse(false,"No Ticket found with Id:"+id,HttpStatus.NOT_FOUND, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
    }
    @Operation(summary = "Update status by multiple ticket")
    @PutMapping("/bulk")
    public ResponseEntity<?> updateMultiplePaymentStatus(@RequestBody UpdatePaymentStatus updatePaymentStatus) {
        List<Ticket> ticketUpdate=new ArrayList<>();
        for(Integer id:updatePaymentStatus.getTicketIds()) {
            for (Ticket ticket : tickets) {
                if (ticket.getTicketId()==id) {
                    ticket.setPaymentStatus(updatePaymentStatus.isPaymentStatus());
                     ticketUpdate.add(ticket);
                }
            }
        }
        ApiResponse<List<Ticket>> response=new ApiResponse<>(true,"Succesfully",HttpStatus.OK,ticketUpdate,LocalDateTime.now());
       return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
