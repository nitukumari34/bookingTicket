package com.bookingTicket.dto;

import com.bookingTicket.entities.BookingSource;
import com.bookingTicket.entities.PaymentMethod;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {

    private Long userId;

    private Long showId;

    private List<Long> seatIds;

    private BookingSource bookingSource = BookingSource.ONLINE;

    private PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;
}