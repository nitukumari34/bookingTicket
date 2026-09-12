package com.bookingTicket.service.payment;

import com.bookingTicket.entities.BookingSource;
import com.bookingTicket.entities.Payment;
import com.bookingTicket.entities.PaymentMethod;

public interface PaymentStrategy {

    Payment processPayment(Long bookingId, double amount, BookingSource source);

    PaymentMethod getSupportedPaymentMethod();
}
