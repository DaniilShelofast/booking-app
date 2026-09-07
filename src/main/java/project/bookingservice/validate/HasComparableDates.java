package project.bookingservice.validate;

import java.time.LocalDate;

public interface HasComparableDates {
    LocalDate getCheckIn();
    LocalDate getCheckOut();
}
