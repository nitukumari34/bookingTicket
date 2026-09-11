package com.bookingTicket.service;

import com.bookingTicket.entities.Hall;
import com.bookingTicket.repositories.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {

    private final HallRepository hallRepository;

    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public Hall addHall(Hall hall) {
        return hallRepository.save(hall);
    }

    public List<Hall> getHallsByCinema(Long cinemaId) {
        return hallRepository.findByCinemaId(cinemaId);
    }

    public Hall getHall(Long id) {
        return hallRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Hall not found"));
    }
}