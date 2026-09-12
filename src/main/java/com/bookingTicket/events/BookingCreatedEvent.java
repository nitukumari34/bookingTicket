package com.bookingTicket.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookingCreatedEvent {
    private final Long bookingId;
    private final Long userId;
    private final Long showId;
    private final List<Long> seatIds;
    private final double amount;
}
