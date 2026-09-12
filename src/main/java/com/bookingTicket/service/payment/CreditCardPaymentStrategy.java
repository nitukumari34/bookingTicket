package com.bookingTicket.service.payment;

import com.bookingTicket.entities.BookingSource;
import com.bookingTicket.entities.Payment;
import com.bookingTicket.entities.PaymentMethod;
import com.bookingTicket.entities.PaymentStatus;
import com.bookingTicket.repositories.PaymentRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreditCardPaymentStrategy implements PaymentStrategy {

    private final PaymentRepository paymentRepository;

    public CreditCardPaymentStrategy(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment processPayment(Long bookingId, double amount, BookingSource source) {
        // Credit card is accepted for BOTH ONLINE and AGENT bookings (R7)
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId("CC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        return paymentRepository.save(payment);
    }

    @Override
    public PaymentMethod getSupportedPaymentMethod() {
        return PaymentMethod.CREDIT_CARD;
    }
}
