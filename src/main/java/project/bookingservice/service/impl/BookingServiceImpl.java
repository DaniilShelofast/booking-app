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
import project.bookingservice.repository.booking.BookingSpecificationBuilder;
import project.bookingservice.service.BookingService;

import java.time.LocalDate;
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
        User user = getUser();

        Accommodation accommodation = accommodationRepository.findById(requestDto.getAccommodationId()).orElseThrow(() -> new EntityNotFoundException("Can't find accommodation by id "));

        int availability = accommodation.getAvailability();
        if (availability <= 0) {
            throw new RuntimeException("Accommodation is not available");
        }

        if (bookingRepository.existsByUserIdAndAccommodationIdAndCheckInBeforeAndCheckOutAfter(user.getId(), accommodation.getId(), requestDto.getCheckOut(), requestDto.getCheckIn())) {
            throw new RuntimeException("You already have a booking for these dates");
        }

        Booking booking = bookingMapper.toModel(requestDto);
        booking.setUser(user);
        accommodation.setAvailability(availability - 1);
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
        return bookingRepository.findAll(build).stream().map(bookingMapper::toDto).toList();
    }

    @Override
    public List<BookingDto> getMyBookings() {
        User user = getUser();
        return bookingRepository.findAllByUserId(user.getId()).stream().map(bookingMapper::toDto).toList();
    }

    @Override
    public BookingDto getBookingById(Long id) {
        User user = getUser();
        Booking booking = bookingRepository.findByIdAndUserId(id, user.getId()).orElseThrow(() -> new EntityNotFoundException("Can't find booking by id " + id));
        return bookingMapper.toDto(booking);
    }

    @Override
    public BookingDto updateBooking(Long id, UpdateBookingRequestDto requestDto) {
        User user = getUser();
        Booking booking = bookingRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find booking by id " + id));

        if (bookingRepository.existsByUserIdAndAccommodationIdAndCheckInBeforeAndCheckOutAfterAndIdNot(
                user.getId(), requestDto.getAccommodationId(), requestDto.getCheckOut(), requestDto.getCheckIn(), booking.getId())
        ) {
            throw new RuntimeException("You have already made a reservation");
        }

        Accommodation oldAccommodation = booking.getAccommodation();
        Accommodation accommodation = accommodationRepository.findById(requestDto.getAccommodationId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find accommodation by id "));

        if (!oldAccommodation.getId().equals(accommodation.getId())) {
            if (accommodation.getAvailability() <= 0) {
                throw new RuntimeException("Accommodation is not available");
            }
            oldAccommodation.setAvailability(oldAccommodation.getAvailability() + 1);
            accommodation.setAvailability(accommodation.getAvailability() - 1);
        }

        bookingMapper.updateBooking(booking, requestDto);
        booking.setAccommodation(accommodation);
        bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    @Override
    public void cancelBooking(Long id) {
        User user = getUser();
        Booking booking = bookingRepository.findByIdAndUserId(id, user.getId()).filter(b -> b.getStatus() == Status.PENDING).orElseThrow(() -> new EntityNotFoundException("Can't find booking by id " + id));
        booking.setStatus(Status.CANCELED);
        Integer availability = booking.getAccommodation().getAvailability();
        booking.getAccommodation().setAvailability(availability + 1);
        bookingRepository.save(booking);
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void checkBookingDates(Long userId, Long bookingId, Long accommodationId, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> allByUserId = bookingRepository.findAllByUserId(userId);

        for (Booking booking : allByUserId) {
            if (!booking.getId().equals(bookingId)
                    && booking.getAccommodation().getId().equals(accommodationId)
                    && booking.getCheckIn().isBefore(checkOut)
                    && booking.getCheckOut().isAfter(checkIn)) {
                throw new RuntimeException("You have already made a reservation");
            }
        }
    }
}
