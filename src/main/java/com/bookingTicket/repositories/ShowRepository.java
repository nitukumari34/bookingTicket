package com.bookingTicket.repositories;

import com.bookingTicket.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository
        extends JpaRepository<Show, Long> {

    List<Show> findByMovieId(Long movieId);

    @Query("""
        SELECT COUNT(s) > 0
        FROM Show s
        WHERE s.hallId = :hallId
        AND s.startTime < :endTime
        AND s.endTime > :startTime
    """)
    boolean existsOverlappingShow(
            @Param("hallId") Long hallId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}