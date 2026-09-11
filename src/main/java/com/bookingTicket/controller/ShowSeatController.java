package com.bookingTicket.controller;

import com.bookingTicket.entities.ShowSeat;
import com.bookingTicket.service.ShowSeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/show-seats")
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    public ShowSeatController(
            ShowSeatService showSeatService) {

        this.showSeatService = showSeatService;
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<ShowSeat>> getSeats(
            @PathVariable Long showId) {

        return ResponseEntity.ok(
                showSeatService.getShowSeats(showId)
        );
    }
}