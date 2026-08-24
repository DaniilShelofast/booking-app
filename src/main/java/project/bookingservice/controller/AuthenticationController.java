package project.bookingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.bookingservice.dto.user.UserDto;
import project.bookingservice.dto.user.UserLoginRequestDto;
import project.bookingservice.dto.user.UserLoginResponseDto;
import project.bookingservice.dto.user.UserRegistrationDto;
import project.bookingservice.exception.RegistrationException;
import project.bookingservice.security.AuthenticationService;
import project.bookingservice.service.RegistrationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public UserDto register(@RequestBody @Valid UserRegistrationDto request)
            throws RegistrationException {
        return registrationService.register(request);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
