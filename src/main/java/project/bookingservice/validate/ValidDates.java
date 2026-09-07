package project.bookingservice.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import project.bookingservice.dto.booking.CreateBookingRequestDto;

public class ValidDates implements ConstraintValidator<FieldMatch, HasComparableDates> {
    @Override
    public boolean isValid(HasComparableDates value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value.getCheckIn() == null || value.getCheckOut() == null) {
            return true;
        }
        return value.getCheckIn().isBefore(value.getCheckOut());
    }
}
