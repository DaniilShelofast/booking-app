package project.bookingservice.validate;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.bookingservice.model.Booking;
import project.bookingservice.model.Status;
import project.bookingservice.repository.BookingRepository;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingExpirationScheduler {
    private final BookingRepository bookingRepository;

    @Scheduled(fixedRate = 300000)
    void checkExpiredSessions() {
        LocalDate startOfToday = LocalDate.now();
        LocalDate endOfTomorrow = LocalDate.now().plusDays(1);
        List<Booking> activeBookings = bookingRepository.findAllByCheckOutBetweenAndStatus(startOfToday, endOfTomorrow, Status.CONFIRMED);
        for (Booking booking : activeBookings) {
            if (booking != null && booking.getStatus() == Status.CONFIRMED) {
                booking.setStatus(Status.EXPIRED);
            } else {
                //todo повідомлення в телеграм, що сьогодні не виявлено нових кімнат
                System.out.println("Today not find available rooms");
            }
        }
        bookingRepository.saveAll(activeBookings);
    }
}
