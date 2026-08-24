package project.bookingservice.dto.user;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UpdateCurrentUser {
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
}
