package project.bookingservice.repository.payment;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.bookingservice.model.Payment;
import project.bookingservice.repository.SpecificationProvider;

@Component
public class PaymentSearchUserSpecificationProvider implements SpecificationProvider<Payment, Long> {
    @Override
    public Specification<Payment> getSpecification(Long param) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), param);
    }
}
