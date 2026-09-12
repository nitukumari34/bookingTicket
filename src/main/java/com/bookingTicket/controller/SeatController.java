package com.bookingTicket.controller;

import com.bookingTicket.entities.Seat;
import com.bookingTicket.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public ResponseEntity<Seat> addSeat(@RequestBody Seat seat) {
        return ResponseEntity.ok(seatService.addSeat(seat));
    }

    @GetMapping("/hall/{hallId}")
    public ResponseEntity<List<Seat>> getSeatsByHall(@PathVariable Long hallId) {
        return ResponseEntity.ok(seatService.getSeatsByHall(hallId));
    }
}
