package project.bookingservice.repository.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.bookingservice.dto.payment.PaymentSearchParametersDto;
import project.bookingservice.model.Payment;
import project.bookingservice.repository.SpecificationBuilder;

@RequiredArgsConstructor
@Component
public class PaymentSpecificationBuilder implements SpecificationBuilder<Payment, PaymentSearchParametersDto> {
    private final PaymentSearchStatusSpecificationProvider statusSpecificationProvider;
    private final PaymentSearchUserSpecificationProvider userSpecificationProvider;

    @Override
    public Specification<Payment> build(PaymentSearchParametersDto searchParametersDto) {
        Specification<Payment> specification = Specification.where(null);

        if (searchParametersDto.status() != null && !searchParametersDto.status().isEmpty()) {
            specification = specification.and(statusSpecificationProvider.getSpecification(searchParametersDto.status()));
        }

        if (searchParametersDto.userId() != null) {
            specification = specification.and(userSpecificationProvider.getSpecification(searchParametersDto.userId()));
        }
        return specification;
    }
}
