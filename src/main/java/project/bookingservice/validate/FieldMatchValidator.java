package project.bookingservice.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import project.bookingservice.dto.user.UserRegistrationDto;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, UserRegistrationDto> {
    @Override
    public boolean isValid(UserRegistrationDto value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value.getPassword() == null) {
            return false;
        }
        if (value.getRepeatPassword() == null) {
            return false;
        }
        return value.getPassword().equals(value.getRepeatPassword());
    }
}
