package com.bookingTicket.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cinemas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String city;

    private String address;
}

//Cinema :One cinema can have multiple halls.
//  |
//          +--- Hall 1
//        +--- Hall 2
//        +--- Hall 3