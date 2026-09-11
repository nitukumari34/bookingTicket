package com.bookingTicket.controller;

import com.bookingTicket.entities.Show;
import com.bookingTicket.service.ShowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping
    public ResponseEntity<Show> createShow(
            @RequestBody Show show) {

        return ResponseEntity.ok(
                showService.createShow(show)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShow(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                showService.getShow(id)
        );
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Show>> getShowsByMovie(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                showService.getShowsByMovie(movieId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(
            @PathVariable Long id) {

        showService.deleteShow(id);

        return ResponseEntity.noContent().build();
    }
}