package com.bookingTicket.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MovieReleasedEvent {
    private final Long movieId;
    private final String title;
    private final String language;
    private final String genre;
    private final LocalDate releaseDate;
}
