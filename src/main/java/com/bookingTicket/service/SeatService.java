package com.bookingTicket.service;

import com.bookingTicket.entities.Seat;
import com.bookingTicket.repositories.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat addSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public List<Seat> getSeatsByHall(Long hallId) {
        return seatRepository.findByHallId(hallId);
    }
}