package com.bookingTicket.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookingModifiedEvent {
    private final Long bookingId;
    private final Long userId;
    private final Long showId;
    private final List<Long> newSeatIds;
    private final double newAmount;
}
