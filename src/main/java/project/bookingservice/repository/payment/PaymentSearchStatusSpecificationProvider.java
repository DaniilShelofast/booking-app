package project.bookingservice.repository.payment;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.bookingservice.model.Payment;
import project.bookingservice.repository.SpecificationProvider;
import java.util.List;

@Component
public class PaymentSearchStatusSpecificationProvider implements SpecificationProvider<Payment, List<String>> {
    @Override
    public Specification<Payment> getSpecification(List<String> params) {
        return (root, query, criteriaBuilder) -> root.get("status").in(params);
    }
}
