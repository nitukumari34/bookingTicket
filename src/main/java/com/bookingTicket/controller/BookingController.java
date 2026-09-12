package com.bookingTicket.controller;

import com.bookingTicket.dto.BookingRequest;
import com.bookingTicket.dto.ModifyBookingRequest;
import com.bookingTicket.entities.Booking;
import com.bookingTicket.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> modifyBooking(
            @PathVariable Long id,
            @RequestBody ModifyBookingRequest request) {
        return ResponseEntity.ok(bookingService.modifyBooking(id, request.getNewSeatIds()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }
}