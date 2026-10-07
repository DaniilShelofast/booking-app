package project.bookingservice.mapper;

import com.stripe.model.checkout.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.bookingservice.config.MapperConfig;
import project.bookingservice.dto.stripe.StripeResponseDto;

@Mapper(config = MapperConfig.class)
public interface StripeMapper {
    @Mapping(target = "sessionId", source = "id")
    @Mapping(target = "sessionUrl", source = "url")
    StripeResponseDto toDto(Session session);
}
