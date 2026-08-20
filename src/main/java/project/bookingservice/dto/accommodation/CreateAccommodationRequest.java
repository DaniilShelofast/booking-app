package project.bookingservice.dto.accommodation;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateAccommodationRequest {
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String size;
    @Column(nullable = false)
    private List<String> amenities;
    @Column(nullable = false)
    @Positive
    private BigDecimal dailyRate;
    @Positive
    private Integer availability;
}
