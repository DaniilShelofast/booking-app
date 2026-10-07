package project.bookingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE accommodations SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String size;
    @Column(nullable = false)
    private List<String> amenities;
    @Column(nullable = false)
    @Positive
    private BigDecimal dailyRate;
    @Min(0)
    private Integer availability;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isDeleted = false;
}
