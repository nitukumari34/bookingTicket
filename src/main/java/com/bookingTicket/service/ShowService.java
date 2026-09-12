package com.bookingTicket.service;

import com.bookingTicket.entities.Show;
import com.bookingTicket.repositories.ShowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final ShowSeatService showSeatService;

    public ShowService(ShowRepository showRepository, ShowSeatService showSeatService) {
        this.showRepository = showRepository;
        this.showSeatService = showSeatService;
    }

    @Transactional
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

        Show savedShow = showRepository.save(show);

        // Auto-generate show seats for this show
        showSeatService.createShowSeats(savedShow);

        return savedShow;
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