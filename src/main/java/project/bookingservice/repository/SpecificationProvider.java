package project.bookingservice.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T, P> {
    Specification<T> getSpecification(P param);
}
