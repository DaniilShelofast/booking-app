package project.bookingservice.repository.booking;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.bookingservice.model.Booking;
import project.bookingservice.repository.SpecificationProvider;

@Component
public class BookingSearchUserSpecificationProvider implements SpecificationProvider<Booking, Long> {
    @Override
    public Specification<Booking> getSpecification(Long param) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), param);
    }
}
