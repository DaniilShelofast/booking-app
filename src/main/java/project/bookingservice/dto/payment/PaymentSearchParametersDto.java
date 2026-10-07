package project.bookingservice.dto.payment;

import java.util.List;

public record PaymentSearchParametersDto(Long userId, List<String> status) {
}
