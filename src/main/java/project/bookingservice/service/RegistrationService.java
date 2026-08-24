package project.bookingservice.service;

import project.bookingservice.dto.user.UserDto;
import project.bookingservice.dto.user.UserRegistrationDto;
import project.bookingservice.exception.RegistrationException;

public interface RegistrationService {
    UserDto register(UserRegistrationDto request) throws RegistrationException;
}
