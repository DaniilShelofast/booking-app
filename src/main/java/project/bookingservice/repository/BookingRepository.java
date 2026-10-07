package project.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import project.bookingservice.model.Booking;
import project.bookingservice.model.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    boolean existsByUserIdAndAccommodationIdAndCheckInBeforeAndCheckOutAfter(
            Long userId,
            Long accommodationId,
            LocalDate checkOut,
            LocalDate checkIn
    );

    boolean existsByUserIdAndAccommodationIdAndCheckInBeforeAndCheckOutAfterAndIdNot(
            Long userId,
            Long accommodationId,
            LocalDate checkOut,
            LocalDate checkIn,
            Long bookingId
    );

    List<Booking> findAllByCheckOutBetweenAndStatus(
            LocalDate start,
            LocalDate end,
            Status status
    );

    List<Booking> findAllByUserId(Long userId);

    Optional<Booking> findByIdAndUserId(Long bookingId, Long userId);
}
