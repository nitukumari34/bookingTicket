package com.bookingTicket.service;


import com.bookingTicket.entities.Movie;
import com.bookingTicket.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Movie not found"));
    }

    public Movie updateMovie(Long id, Movie movie) {

        Movie existingMovie = getMovieById(id);

        existingMovie.setTitle(movie.getTitle());
        existingMovie.setLanguage(movie.getLanguage());
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setReleaseDate(movie.getReleaseDate());
        existingMovie.setDuration(movie.getDuration());

        return movieRepository.save(existingMovie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public List<Movie> searchByTitle(String title) {
        return movieRepository
                .findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> searchByLanguage(String language) {
        return movieRepository
                .findByLanguageIgnoreCase(language);
    }

    public List<Movie> searchByGenre(String genre) {
        return movieRepository
                .findByGenreIgnoreCase(genre);
    }

    public List<Movie> searchByReleaseDate(LocalDate date) {
        return movieRepository
                .findByReleaseDate(date);
    }
}