package com.bookingTicket.controller;

import com.bookingTicket.entities.Hall;
import com.bookingTicket.service.HallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/halls")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @PostMapping
    public ResponseEntity<Hall> addHall(
            @RequestBody Hall hall) {

        return ResponseEntity.ok(
                hallService.addHall(hall)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hall> getHall(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                hallService.getHall(id)
        );
    }

    @GetMapping("/cinema/{cinemaId}")
    public ResponseEntity<List<Hall>> getHalls(
            @PathVariable Long cinemaId) {

        return ResponseEntity.ok(
                hallService.getHallsByCinema(cinemaId)
        );
    }
}