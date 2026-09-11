package com.bookingTicket.controller;

import com.bookingTicket.entities.Cinema;
import com.bookingTicket.service.CinemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cinemas")
public class CinemaController {

    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @PostMapping
    public ResponseEntity<Cinema> addCinema(
            @RequestBody Cinema cinema) {

        return ResponseEntity.ok(
                cinemaService.addCinema(cinema)
        );
    }

    @GetMapping
    public ResponseEntity<List<Cinema>> getAllCinemas() {

        return ResponseEntity.ok(
                cinemaService.getAllCinemas()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cinema> getCinema(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                cinemaService.getCinema(id)
        );
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Cinema>> getByCity(
            @PathVariable String city) {

        return ResponseEntity.ok(
                cinemaService.getCinemasByCity(city)
        );
    }
}