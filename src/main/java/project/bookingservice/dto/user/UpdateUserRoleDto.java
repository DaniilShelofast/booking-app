package project.bookingservice.dto.user;

import jakarta.persistence.Column;
import lombok.Data;
import java.util.Set;

@Data
public class UpdateUserRoleDto {
    @Column(nullable = false)
    private Set<String> roles;
}
