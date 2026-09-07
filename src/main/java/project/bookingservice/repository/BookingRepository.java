package project.bookingservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import project.bookingservice.model.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    boolean existsByAccommodationIdAndCheckInBeforeAndCheckOutAfter(
            Long accommodationId, LocalDate checkOut, LocalDate checkIn
    );

    List<Booking> findAllByUserId(Long userId);

    Optional<Booking> findByIdAndUserId(Long bookingId, Long userId);
}
