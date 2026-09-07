package project.bookingservice.dto.booking;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import project.bookingservice.validate.FieldMatch;
import project.bookingservice.validate.HasComparableDates;

import java.time.LocalDate;

@FieldMatch(first = "checkIn", second = "checkOut", message = "The check-in date must be less than the check-out date ")
@Data
public class CreateBookingRequestDto implements HasComparableDates {
    @Column(nullable = false)
    private Long accommodationId;
    @Column(nullable = false)
    @FutureOrPresent
    private LocalDate checkIn;
    @Column(nullable = false)
    @Future
    private LocalDate checkOut;
}
