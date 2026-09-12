package com.bookingTicket.service;

import com.bookingTicket.dto.BookingRequest;
import com.bookingTicket.entities.*;
import com.bookingTicket.events.BookingCancelledEvent;
import com.bookingTicket.events.BookingCreatedEvent;
import com.bookingTicket.events.BookingModifiedEvent;
import com.bookingTicket.repositories.BookingRepository;
import com.bookingTicket.repositories.BookingSeatRepository;
import com.bookingTicket.repositories.ShowSeatRepository;
import com.bookingTicket.service.payment.PaymentService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowSeatRepository showSeatRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final PaymentService paymentService;
    private final ApplicationEventPublisher eventPublisher;

    public BookingService(
            BookingRepository bookingRepository,
            ShowSeatRepository showSeatRepository,
            BookingSeatRepository bookingSeatRepository,
            PaymentService paymentService,
            ApplicationEventPublisher eventPublisher) {

        this.bookingRepository = bookingRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.paymentService = paymentService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Booking createBooking(BookingRequest request) {
        Long userId = request.getUserId();
        Long showId = request.getShowId();
        List<Long> seatIds = request.getSeatIds();

        // 1. Lock selected seats with pessimistic write lock (R11)
        List<ShowSeat> showSeats = showSeatRepository.findSeatToUpdate(showId, seatIds);

        // 2. Validate all requested seats exist (R8, R10)
        if (showSeats.size() != seatIds.size()) {
            throw new RuntimeException("One or more seats are invalid");
        }

        // 3. Check availability
        for (ShowSeat showSeat : showSeats) {
            if (showSeat.getStatus() != SeatStatus.AVAILABLE) {
                throw new RuntimeException("Seat " + showSeat.getSeatId() + " is already booked");
            }
        }

        // 4. Calculate total amount
        double amount = showSeats.stream()
                .mapToDouble(ShowSeat::getPrice)
                .sum();

        // 5. Mark seats as BOOKED (R13)
        for (ShowSeat showSeat : showSeats) {
            showSeat.setStatus(SeatStatus.BOOKED);
        }
        showSeatRepository.saveAll(showSeats);

        // 6. Create booking record
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setShowId(showId);
        booking.setAmount(amount);
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking savedBooking = bookingRepository.save(booking);

        // 7. Save Booking-Seat mappings
        List<BookingSeat> bookingSeats = new ArrayList<>();
        for (ShowSeat showSeat : showSeats) {
            BookingSeat bs = new BookingSeat();
            bs.setBookingId(savedBooking.getId());
            bs.setShowSeatId(showSeat.getId());
            bookingSeats.add(bs);
        }
        bookingSeatRepository.saveAll(bookingSeats);

        // 8. Process Payment using Strategy Pattern (R6, R7)
        paymentService.processPayment(
                savedBooking.getId(),
                amount,
                request.getBookingSource(),
                request.getPaymentMethod()
        );

        // 9. Dispatch Async Notification (R14)
        eventPublisher.publishEvent(new BookingCreatedEvent(
                savedBooking.getId(),
                userId,
                showId,
                seatIds,
                amount
        ));

        return savedBooking;
    }

    @Transactional
    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        if (booking.getStatus() == BookingStatus.CANCELED) {
            throw new RuntimeException("Booking is already canceled");
        }

        // 1. Find mapped show seats and release them back to AVAILABLE
        List<BookingSeat> bookingSeats = bookingSeatRepository.findByBookingId(bookingId);
        List<Long> showSeatIds = bookingSeats.stream()
                .map(BookingSeat::getShowSeatId)
                .toList();

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        for (ShowSeat showSeat : showSeats) {
            showSeat.setStatus(SeatStatus.AVAILABLE);
        }
        showSeatRepository.saveAll(showSeats);

        // 2. Update booking status
        booking.setStatus(BookingStatus.CANCELED);
        Booking updatedBooking = bookingRepository.save(booking);

        // 3. Dispatch Async Cancellation Notification (R14)
        eventPublisher.publishEvent(new BookingCancelledEvent(
                updatedBooking.getId(),
                updatedBooking.getUserId(),
                updatedBooking.getShowId(),
                updatedBooking.getAmount()
        ));

        return updatedBooking;
    }

    @Transactional
    public Booking modifyBooking(Long bookingId, List<Long> newSeatIds) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        if (booking.getStatus() == BookingStatus.CANCELED) {
            throw new RuntimeException("Cannot modify a canceled booking");
        }

        // Validate newSeatIds is not empty
        if (newSeatIds == null || newSeatIds.isEmpty()) {
            throw new RuntimeException("newSeatIds must not be empty. Provide at least one seat.");
        }

        // 1. Release previous seats
        List<BookingSeat> oldBookingSeats = bookingSeatRepository.findByBookingId(bookingId);
        List<Long> oldShowSeatIds = oldBookingSeats.stream()
                .map(BookingSeat::getShowSeatId)
                .toList();

        List<ShowSeat> oldShowSeats = showSeatRepository.findAllById(oldShowSeatIds);
        for (ShowSeat oldSeat : oldShowSeats) {
            oldSeat.setStatus(SeatStatus.AVAILABLE);
        }
        showSeatRepository.saveAll(oldShowSeats);
        bookingSeatRepository.deleteAll(oldBookingSeats);

        // 2. Lock & Validate new seats
        List<ShowSeat> newShowSeats = showSeatRepository.findSeatToUpdate(booking.getShowId(), newSeatIds);
        if (newShowSeats.size() != newSeatIds.size()) {
            throw new RuntimeException("One or more new seats are invalid");
        }

        for (ShowSeat newSeat : newShowSeats) {
            if (newSeat.getStatus() != SeatStatus.AVAILABLE) {
                throw new RuntimeException("Seat " + newSeat.getSeatId() + " is already booked");
            }
        }

        // 3. Mark new seats as BOOKED
        for (ShowSeat newSeat : newShowSeats) {
            newSeat.setStatus(SeatStatus.BOOKED);
        }
        showSeatRepository.saveAll(newShowSeats);

        // 4. Update Booking Seats
        List<BookingSeat> newBookingSeats = new ArrayList<>();
        for (ShowSeat newSeat : newShowSeats) {
            BookingSeat bs = new BookingSeat();
            bs.setBookingId(booking.getId());
            bs.setShowSeatId(newSeat.getId());
            newBookingSeats.add(bs);
        }
        bookingSeatRepository.saveAll(newBookingSeats);

        // 5. Update Amount
        double newAmount = newShowSeats.stream()
                .mapToDouble(ShowSeat::getPrice)
                .sum();
        booking.setAmount(newAmount);
        Booking savedBooking = bookingRepository.save(booking);

        // 6. Dispatch Async Modification Notification (R15)
        eventPublisher.publishEvent(new BookingModifiedEvent(
                savedBooking.getId(),
                savedBooking.getUserId(),
                savedBooking.getShowId(),
                newSeatIds,
                newAmount
        ));

        return savedBooking;
    }

    public Booking getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
}