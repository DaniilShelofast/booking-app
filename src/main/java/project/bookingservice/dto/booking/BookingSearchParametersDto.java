package project.bookingservice.dto.booking;

import java.util.List;

public record BookingSearchParametersDto(Long userId, List<String> status ) {
}
