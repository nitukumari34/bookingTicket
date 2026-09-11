package com.bookingTicket.controller;

import com.bookingTicket.entities.Movie;
import com.bookingTicket.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(
            @RequestBody Movie movie) {

        return ResponseEntity.ok(
                movieService.addMovie(movie)
        );
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {

        return ResponseEntity.ok(
                movieService.getAllMovies()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                movieService.getMovieById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable Long id,
            @RequestBody Movie movie) {

        return ResponseEntity.ok(
                movieService.updateMovie(id, movie)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(
            @PathVariable Long id) {

        movieService.deleteMovie(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<Movie>> searchByTitle(
            @RequestParam String title) {

        return ResponseEntity.ok(
                movieService.searchByTitle(title)
        );
    }

    @GetMapping("/search/language")
    public ResponseEntity<List<Movie>> searchByLanguage(
            @RequestParam String language) {

        return ResponseEntity.ok(
                movieService.searchByLanguage(language)
        );
    }

    @GetMapping("/search/genre")
    public ResponseEntity<List<Movie>> searchByGenre(
            @RequestParam String genre) {

        return ResponseEntity.ok(
                movieService.searchByGenre(genre)
        );
    }

    @GetMapping("/search/release-date")
    public ResponseEntity<List<Movie>> searchByReleaseDate(
            @RequestParam LocalDate date) {

        return ResponseEntity.ok(
                movieService.searchByReleaseDate(date)
        );
    }
}