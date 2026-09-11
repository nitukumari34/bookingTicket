package com.bookingTicket.service;
import com.bookingTicket.entities.*;
import com.bookingTicket.repositories.SeatRepository;
import com.bookingTicket.repositories.ShowSeatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowSeatService {

    private final ShowSeatRepository showSeatRepository;
    private final SeatRepository seatRepository;

    public ShowSeatService(
            ShowSeatRepository showSeatRepository,
            SeatRepository seatRepository) {

        this.showSeatRepository = showSeatRepository;
        this.seatRepository = seatRepository;
    }

    public void createShowSeats(Show show) {

        List<Seat> seats =
                seatRepository.findByHallId(show.getHallId());

        List<ShowSeat> showSeats = new ArrayList<>();

        for (Seat seat : seats) {

            ShowSeat showSeat = new ShowSeat();

            showSeat.setShowId(show.getId());
            showSeat.setSeatId(seat.getId());
            showSeat.setStatus(SeatStatus.AVAILABLE);
            showSeat.setPrice(seat.getPrice());

            showSeats.add(showSeat);
        }

        showSeatRepository.saveAll(showSeats);
    }

    public List<ShowSeat> getShowSeats(Long showId) {

        return showSeatRepository.findByShowId(showId);
    }
}