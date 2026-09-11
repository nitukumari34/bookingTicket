package com.bookingTicket.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "show_seats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long showId;

    private Long seatId;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    private double price;
}