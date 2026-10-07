package project.bookingservice.dto.payment;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentResponseDto {
    private Long id;
    private String status;
    private Long bookingId;
    private String sessionUrl;
    private String sessionId;
    private BigDecimal amountToPay;
}
