# Movie Ticket Booking – Backend

A scalable backend system for a movie ticket booking platform built with Spring Boot, JPA/Hibernate, and an event-driven notification architecture.

---

## 📌 Requirements & Problem Statement

- **R1:** Multi-cinema & multi-city support. Each cinema contains multiple screening halls.
- **R2:** A cinema can schedule multiple shows per movie, but a hall can host only one show at a time.
- **R3:** Browse and view all available showtimes for any selected movie across all cinemas.
- **R4:** Search movies by title, language, genre, or release date.
- **R5:** Seat booking for any available showtime at any cinema hall.
- **R6:** Support for both Online and In-Person (Ticket Agent) booking channels.
- **R7:** Payment method rules:
  - **Online bookings:** Credit Card only.
  - **In-person bookings:** Cash or Credit Card through a ticket agent.
- **R8:** Multi-seat selection in a single booking transaction.
- **R9:** Fixed seat categories with differentiated pricing: **Silver**, **Gold**, and **Platinum**.
- **R10:** Single-ticket allocation per seat (strict 1:1 mapping).
- **R11:** Concurrency control to prevent multiple users from booking the same seat for the same show.
- **R12:** Admin operations to add, delete, or update shows and movies.
- **R13:** Clear visual distinction between available and booked seats.
- **R14:** Event-driven notification system:
  - Broadcast notification to opted-in users when a new movie is released.
  - Booking confirmation notification upon ticket purchase.
  - Cancellation notification with refund details when a booking is canceled.
- **R15:** Notification to the customer whenever a booking is modified, sharing updated seats and pricing details.

---

## 🚀 Implemented Features

- **Movie Management:** Full CRUD operations and case-insensitive search by title, language, genre, and release date.
- **User & Preference Management:** User registration and tracking of notification opt-in preferences.
- **Cinema & Hall Hierarchy:** Multi-city cinema structures with multiple halls and distinct seat layouts.
- **Tiered Seat Pricing:** Seat categories (Silver, Gold, Platinum) with individual price calculations.
- **Show Scheduling:** Conflict-free show allocation per hall.
- **Pessimistic Locking & Concurrency Control:** Database-level locking (`findSeatToUpdate` with write locks) to eliminate race conditions and double-booking.
- **Pluggable Payment Processing (Strategy Pattern):**
  - Enforces payment rules dynamically based on booking source (e.g. Cash only allowed via ticket agents).
- **Event-Driven Asynchronous Notifications:** Decoupled event publishers and listeners for booking creation, cancellation, modification, and movie releases.

---

## 🧱 Core Entities

- **Cinema:** Physical venue located in a city.
- **Hall:** Screening room belonging to a cinema.
- **Seat:** Static physical seat configuration in a hall with a tier category (`SILVER`, `GOLD`, `PLATINUM`).
- **Movie:** Film details including title, language, genre, duration, and release date.
- **Show:** A scheduled screening of a movie in a specific hall at a particular time.
- **ShowSeat:** Bridge entity representing the real-time status (`AVAILABLE`, `BOOKED`) and price of a seat for a specific show.
- **Booking:** Confirmed reservation linking a user, show, amount, and payment status.
- **BookingSeat:** Association between a booking and the specific show seats reserved.
- **User:** Customer details and communication preferences.
- **Payment:** Transaction record capturing payment method, status, and transaction reference.

---

## 💡 Key Design Decisions

### Separation of `Seat` and `ShowSeat`
A static `Seat` represents the physical chair inside a cinema hall (row, number, tier). In contrast, `ShowSeat` represents that seat's state for a **specific show instance**. 

This separation brings several benefits:
- **Independent Show Availability:** The same physical seat can be booked for a 3:00 PM show and available for a 6:00 PM show without conflicting state.
- **Dynamic Pricing:** Allows pricing adjustments per show (e.g., weekend pricing vs. weekday matinee) while retaining the base physical tier.
- **Efficient Concurrency Control:** Pessimistic write locks are placed strictly on the `ShowSeat` records for that specific show, preventing double-bookings without locking the entire physical auditorium or unrelated shows.