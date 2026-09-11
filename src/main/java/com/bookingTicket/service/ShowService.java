package com.bookingTicket.service;

import com.bookingTicket.entities.Show;
import com.bookingTicket.repositories.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public Show createShow(Show show) {

        boolean overlap =
                showRepository.existsOverlappingShow(
                        show.getHallId(),
                        show.getStartTime(),
                        show.getEndTime()
                );

        if (overlap) {
            throw new RuntimeException(
                    "Hall already has a show at this time"
            );
        }

        return showRepository.save(show);
    }

    public List<Show> getShowsByMovie(Long movieId) {
        return showRepository.findByMovieId(movieId);
    }

    public Show getShow(Long id) {
        return showRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Show not found"));
    }

    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }
}