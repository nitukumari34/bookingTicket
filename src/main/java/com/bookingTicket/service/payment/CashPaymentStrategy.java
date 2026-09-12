package com.bookingTicket.service.payment;

import com.bookingTicket.entities.BookingSource;
import com.bookingTicket.entities.Payment;
import com.bookingTicket.entities.PaymentMethod;
import com.bookingTicket.entities.PaymentStatus;
import com.bookingTicket.repositories.PaymentRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CashPaymentStrategy implements PaymentStrategy {

    private final PaymentRepository paymentRepository;

    public CashPaymentStrategy(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment processPayment(Long bookingId, double amount, BookingSource source) {
        // Enforce Requirement R7: Cash payment is ONLY allowed via ticket AGENT
        if (source != BookingSource.AGENT) {
            throw new IllegalArgumentException(
                    "Cash payment is only allowed for in-person bookings through a Ticket Agent (R7)."
            );
        }

        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setPaymentMethod(PaymentMethod.CASH);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId("CASH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        return paymentRepository.save(payment);
    }

    @Override
    public PaymentMethod getSupportedPaymentMethod() {
        return PaymentMethod.CASH;
    }
}
