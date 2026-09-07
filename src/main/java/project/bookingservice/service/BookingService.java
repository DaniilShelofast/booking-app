package project.bookingservice.service;

import project.bookingservice.dto.booking.BookingDto;
import project.bookingservice.dto.booking.BookingSearchParametersDto;
import project.bookingservice.dto.booking.CreateBookingRequestDto;
import project.bookingservice.dto.booking.UpdateBookingRequestDto;
import java.util.List;

public interface BookingService {
    BookingDto createBooking(CreateBookingRequestDto requestDto);

    List<BookingDto> getBookingsByUserIdAndStatus(BookingSearchParametersDto searchParametersDto);

    List<BookingDto> getMyBookings();

    BookingDto getBookingById(Long id);

    BookingDto updateBooking(Long id, UpdateBookingRequestDto requestDto);

    void cancelBooking(Long id);
}
