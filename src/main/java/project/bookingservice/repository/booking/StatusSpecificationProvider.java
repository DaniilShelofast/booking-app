package project.bookingservice.repository.booking;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.bookingservice.model.Booking;
import project.bookingservice.repository.SpecificationProvider;
import java.util.List;

@Component
public class StatusSpecificationProvider implements SpecificationProvider<Booking, List<String>> {
    @Override
    public Specification<Booking> getSpecification(List<String> params) {
        return (root, query, criteriaBuilder) -> root.get("status").in(params);
    }
}
