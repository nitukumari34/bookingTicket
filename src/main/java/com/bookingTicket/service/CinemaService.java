package com.bookingTicket.service;

import com.bookingTicket.entities.Cinema;
import com.bookingTicket.repositories.CinemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {

    private final CinemaRepository cinemaRepository;

    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public Cinema addCinema(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Cinema getCinema(Long id) {
        return cinemaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cinema not found"));
    }

    public List<Cinema> getCinemasByCity(String city) {
        return cinemaRepository.findByCityIgnoreCase(city);
    }
}