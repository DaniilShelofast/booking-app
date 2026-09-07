package project.bookingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bookingservice.dto.booking.BookingDto;
import project.bookingservice.dto.booking.BookingSearchParametersDto;
import project.bookingservice.dto.booking.CreateBookingRequestDto;
import project.bookingservice.dto.booking.UpdateBookingRequestDto;
import project.bookingservice.exception.EntityNotFoundException;
import project.bookingservice.mapper.BookingMapper;
import project.bookingservice.model.Accommodation;
import project.bookingservice.model.Booking;
import project.bookingservice.model.Status;
import project.bookingservice.model.User;
import project.bookingservice.repository.AccommodationRepository;
import project.bookingservice.repository.BookingRepository;
import project.bookingservice.repository.BookingSpecificationBuilder;
import project.bookingservice.service.BookingService;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingSpecificationBuilder specificationBuilder;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final AccommodationRepository accommodationRepository;

    @Override
    public BookingDto createBooking(CreateBookingRequestDto requestDto) {
        Accommodation accommodation = accommodationRepository.findById(
                        requestDto.getAccommodationId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find accommodation by id ")
                );

        if (accommodation.getAvailability() < 0) {
            throw new RuntimeException("accommodation not available ");
        }

        if (bookingRepository.existsByAccommodationIdAndCheckInBeforeAndCheckOutAfter(
                accommodation.getId(), requestDto.getCheckOut(), requestDto.getCheckIn())) {
            throw new RuntimeException("Accommodation not available on these dates ");
        }

        User user = getUser();
        Booking booking = bookingMapper.toModel(requestDto);
        booking.setUser(user);
        booking.setAccommodation(accommodation);
        booking.setCheckIn(requestDto.getCheckIn());
        booking.setCheckOut(requestDto.getCheckOut());
        booking.setStatus(Status.PENDING);

        bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    @Override
    public List<BookingDto> getBookingsByUserIdAndStatus(BookingSearchParametersDto searchParametersDto) {
        Specification<Booking> build = specificationBuilder.build(searchParametersDto);
        return bookingRepository.findAll(build)
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public List<BookingDto> getMyBookings() {
        User user = getUser();
        return bookingRepository.findAllByUserId(user.getId())
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public BookingDto getBookingById(Long id) {
        User user = getUser();
        Booking booking = bookingRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find booking by id " + id));
        return bookingMapper.toDto(booking);
    }

    @Override
    public BookingDto updateBooking(Long id, UpdateBookingRequestDto requestDto) {
        User user = getUser();
        Booking booking = bookingRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find booking by id " + id));

        Accommodation accommodation = accommodationRepository.findById(
                        requestDto.getAccommodationId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find accommodation by id ")
                );

        if (accommodation.getAvailability() < 0) {
            throw new RuntimeException("accommodation not available ");
        }

        if (bookingRepository.existsByAccommodationIdAndCheckInBeforeAndCheckOutAfter(
                accommodation.getId(), requestDto.getCheckOut(), requestDto.getCheckIn())) {
            throw new RuntimeException("Accommodation not available on these dates ");
        }

        bookingMapper.updateBooking(booking, requestDto);
        booking.setAccommodation(accommodation);
        bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    @Override
    public void cancelBooking(Long id) {
        User user = getUser();
        Booking b = bookingRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find booking by id " + id));
        bookingRepository.deleteById(b.getId());
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
