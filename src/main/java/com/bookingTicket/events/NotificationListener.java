package com.bookingTicket.events;

import com.bookingTicket.entities.User;
import com.bookingTicket.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);
    private final UserRepository userRepository;

    public NotificationListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Async
    @EventListener
    public void onBookingCreated(BookingCreatedEvent event) {
        userRepository.findById(event.getUserId()).ifPresent(user -> {
            log.info("🔔 [NOTIFICATION - BOOKING CONFIRMED] Sent to: {} ({}) | Booking ID: {} | Seats: {} | Amount: ₹{}",
                    user.getName(), user.getEmail(), event.getBookingId(), event.getSeatIds(), event.getAmount());
        });
    }

    @Async
    @EventListener
    public void onBookingCancelled(BookingCancelledEvent event) {
        userRepository.findById(event.getUserId()).ifPresent(user -> {
            log.info("🔔 [NOTIFICATION - BOOKING CANCELLED] Sent to: {} ({}) | Booking ID: {} | Refund Initiated: ₹{}",
                    user.getName(), user.getEmail(), event.getBookingId(), event.getRefundAmount());
        });
    }

    @Async
    @EventListener
    public void onBookingModified(BookingModifiedEvent event) {
        userRepository.findById(event.getUserId()).ifPresent(user -> {
            log.info("🔔 [NOTIFICATION - BOOKING MODIFIED] Sent to: {} ({}) | Booking ID: {} | New Seats: {} | Total: ₹{}",
                    user.getName(), user.getEmail(), event.getBookingId(), event.getNewSeatIds(), event.getNewAmount());
        });
    }

    @Async
    @EventListener
    public void onMovieReleased(MovieReleasedEvent event) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.isOptedInForNotifications()) {
                log.info("🎬 [NOTIFICATION - NEW MOVIE RELEASE] Sent to: {} ({}) | Movie: '{}' | Genre: {} | Language: {} | Release Date: {}",
                        user.getName(), user.getEmail(), event.getTitle(), event.getGenre(), event.getLanguage(), event.getReleaseDate());
            }
        }
    }
}
