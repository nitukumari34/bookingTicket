package com.bookingTicket.repositories;


import com.bookingTicket.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByLanguageIgnoreCase(String language);

    List<Movie> findByGenreIgnoreCase(String genre);

    List<Movie> findByReleaseDate(LocalDate releaseDate);
}
