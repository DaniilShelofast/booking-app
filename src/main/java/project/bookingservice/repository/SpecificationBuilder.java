package project.bookingservice.repository;

import org.springframework.data.jpa.domain.Specification;
import project.bookingservice.dto.booking.BookingSearchParametersDto;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookingSearchParametersDto searchParametersDto);
}
