package com.bookingTicket.service.payment;

import com.bookingTicket.entities.BookingSource;
import com.bookingTicket.entities.Payment;
import com.bookingTicket.entities.PaymentMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final Map<PaymentMethod, PaymentStrategy> strategyMap;

    public PaymentService(List<PaymentStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        PaymentStrategy::getSupportedPaymentMethod,
                        Function.identity()
                ));
    }

    public Payment processPayment(Long bookingId, double amount, BookingSource source, PaymentMethod method) {
        PaymentStrategy strategy = strategyMap.get(method);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }

        return strategy.processPayment(bookingId, amount, source);
    }
}
