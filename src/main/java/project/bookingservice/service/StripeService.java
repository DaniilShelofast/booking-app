package project.bookingservice.service;

import project.bookingservice.dto.stripe.SessionRequestDto;
import project.bookingservice.dto.stripe.StripeResponseDto;

public interface StripeService {
    StripeResponseDto createSession(SessionRequestDto requestDto);
}
