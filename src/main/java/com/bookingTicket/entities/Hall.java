package com.bookingTicket.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "halls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int capacity;

    private Long cinemaId;
}
//Hall: A cinema has multiple halls.
//
//  Cinema
//  |
//  +---- Hall 1
//        |
//        +---- Hall 2