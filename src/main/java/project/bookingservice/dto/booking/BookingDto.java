package project.bookingservice.dto.booking;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingDto {
    private Long id;
    private Long userId;
    private Long accommodationId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status;
}
