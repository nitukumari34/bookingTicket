package com.bookingTicket.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hallId;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private double price;
}