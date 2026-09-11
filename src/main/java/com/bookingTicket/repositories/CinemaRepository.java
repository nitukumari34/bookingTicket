package com.bookingTicket.repositories;


import com.bookingTicket.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaRepository
        extends JpaRepository<Cinema, Long> {

    List<Cinema> findByCityIgnoreCase(String city);
}