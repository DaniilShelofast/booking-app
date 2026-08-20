package project.bookingservice.dto.accommodation;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AccommodationDto {
    private Long id;
    private String type;
    private String address;
    private String size;
    private List<String> amenities;
    private BigDecimal dailyRate;
    private Integer availability;
}
