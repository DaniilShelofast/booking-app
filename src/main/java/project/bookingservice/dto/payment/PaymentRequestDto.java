package project.bookingservice.dto.payment;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PaymentRequestDto {
    @Column(nullable = false)
    private Long bookingId;
}
