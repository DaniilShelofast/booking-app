package project.bookingservice.repository.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.bookingservice.dto.booking.BookingSearchParametersDto;
import project.bookingservice.model.Booking;
import project.bookingservice.repository.SpecificationBuilder;

@RequiredArgsConstructor
@Component
public class BookingSpecificationBuilder implements SpecificationBuilder<Booking, BookingSearchParametersDto> {
    private final BookingSearchStatusSpecificationProvider statusSpecificationProvider;
    private final BookingSearchUserSpecificationProvider userSpecificationProvider;

    @Override
    public Specification<Booking> build(BookingSearchParametersDto searchParametersDto) {
        Specification<Booking> specification = Specification.where(null);

        if (searchParametersDto.status() != null && !searchParametersDto.status().isEmpty()) {
            specification = specification.and(statusSpecificationProvider.getSpecification(searchParametersDto.status()));
        }

        if (searchParametersDto.userId() != null) {
            specification = specification.and(userSpecificationProvider.getSpecification(searchParametersDto.userId()));
        }
        return specification;
    }
}
