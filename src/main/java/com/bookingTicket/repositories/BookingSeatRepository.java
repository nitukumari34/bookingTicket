package com.bookingTicket.repositories;

import com.bookingTicket.entities.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {

    List<BookingSeat> findByBookingId(Long bookingId);

    void deleteByBookingId(Long bookingId);
}
