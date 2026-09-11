package com.bookingTicket.repositories;

import com.bookingTicket.entities.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    //  used for booking concurrency
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT s FROM ShowSeat s
            WHERE s.showId = :showId
            AND s.seatId IN :seatIds
            ORDER BY s.seatId
            """)
    List<ShowSeat> findSeatToUpdate(
            Long showId,
            List<Long> seatIds
    );

    //  - get all seats for a show
    List<ShowSeat> findByShowId(Long showId);
}