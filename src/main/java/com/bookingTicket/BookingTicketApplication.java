package com.bookingTicket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BookingTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingTicketApplication.class, args);
	}

}
