package com.bookingTicket.service;

import com.bookingTicket.entities.Booking;
import com.bookingTicket.entities.BookingStatus;
import com.bookingTicket.entities.SeatStatus;
import com.bookingTicket.entities.ShowSeat;
import com.bookingTicket.repositories.BookingRepository;
import com.bookingTicket.repositories.ShowSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowSeatRepository showSeatRepository;

    public BookingService(
            BookingRepository bookingRepository,
            ShowSeatRepository showSeatRepository) {

        this.bookingRepository = bookingRepository;
        this.showSeatRepository = showSeatRepository;
    }

    @Transactional
    public Booking createBooking(
            Long userId,
            Long showId,
            List<Long> seatIds) {

        // 1. Lock selected seats
        List<ShowSeat> showSeats =
                showSeatRepository.findSeatToUpdate(
                        showId,
                        seatIds
                );

        // 2. Validate all requested seats exist
        if (showSeats.size() != seatIds.size()) {
            throw new RuntimeException(
                    "One or more seats are invalid"
            );
        }

        // 3. Check availability
        for (ShowSeat showSeat : showSeats) {

            if (showSeat.getStatus() != SeatStatus.AVAILABLE) {

                throw new RuntimeException(
                        "Seat " +
                                showSeat.getSeatId() +
                                " is already booked"
                );
            }
        }

        // 4. Calculate total amount
        double amount = showSeats.stream()
                .mapToDouble(ShowSeat::getPrice)
                .sum();

        // 5. Mark seats as BOOKED
        for (ShowSeat showSeat : showSeats) {
            showSeat.setStatus(SeatStatus.BOOKED);
        }

        showSeatRepository.saveAll(showSeats);

        // 6. Create booking
        Booking booking = new Booking();

        booking.setUserId(userId);
        booking.setShowId(showId);
        booking.setAmount(amount);
        booking.setStatus(
                BookingStatus.CONFIRMED
        );

        Booking savedBooking =
                bookingRepository.save(booking);

        return savedBooking;
    }
}