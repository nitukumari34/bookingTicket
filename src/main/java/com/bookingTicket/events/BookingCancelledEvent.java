package com.bookingTicket.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookingCancelledEvent {
    private final Long bookingId;
    private final Long userId;
    private final Long showId;
    private final double refundAmount;
}
