package project.bookingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.bookingservice.dto.booking.BookingDto;
import project.bookingservice.dto.booking.BookingSearchParametersDto;
import project.bookingservice.dto.booking.CreateBookingRequestDto;
import project.bookingservice.dto.booking.UpdateBookingRequestDto;
import project.bookingservice.service.BookingService;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto createBooking(@RequestBody @Valid CreateBookingRequestDto requestDto) {
        return bookingService.createBooking(requestDto);
    }

    //todo check method
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<BookingDto> getBookingsByUserIdAndStatus(
            BookingSearchParametersDto searchParametersDto
    ) {
        return bookingService.getBookingsByUserIdAndStatus(searchParametersDto);
    }

    @GetMapping("/my")
    public List<BookingDto> getMyBookings() {
        return bookingService.getMyBookings();
    }

    @GetMapping("/{id}")
    public BookingDto getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/{id}")
    public BookingDto updateBooking(
            @PathVariable Long id,
            @RequestBody @Valid UpdateBookingRequestDto requestDto) {
        return bookingService.updateBooking(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }
}
