package com.bookingTicket.controller;

import com.bookingTicket.entities.Booking;
import com.bookingTicket.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(
            BookingService bookingService) {

        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long userId,
            @RequestParam Long showId,
            @RequestBody List<Long> seatIds) {

        Booking booking =
                bookingService.createBooking(
                        userId,
                        showId,
                        seatIds
                );

        return ResponseEntity.ok(booking);
    }
}

//POST /bookings
//body
//{
//    "userId": 1,
//        "showId": 1,
//        "seatIds": [1, 2, 3]
//}

//BookingController
//       ↓
//BookingService
//       ↓
//ShowSeatRepository
//       ↓
//PESSIMISTIC_WRITE
//       ↓
//Check AVAILABLE
//       ↓
//Calculate ₹
//       ↓
//BOOKED
//       ↓
//Create Booking
//       ↓
//Create BookingSeat
//       ↓
//COMMIT