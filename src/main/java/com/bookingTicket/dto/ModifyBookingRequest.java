package com.bookingTicket.dto;

import lombok.Data;

import java.util.List;

@Data
public class ModifyBookingRequest {

    private List<Long> newSeatIds;
}
