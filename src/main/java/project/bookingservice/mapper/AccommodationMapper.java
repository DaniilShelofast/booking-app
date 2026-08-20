package project.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import project.bookingservice.config.MapperConfig;
import project.bookingservice.dto.accommodation.AccommodationDto;
import project.bookingservice.dto.accommodation.CreateAccommodationRequest;
import project.bookingservice.model.Accommodation;

@Mapper(config = MapperConfig.class)
public interface AccommodationMapper {
    AccommodationDto toDto(Accommodation request);

    Accommodation toEntity(CreateAccommodationRequest request);

    void updateAccommodation(@MappingTarget Accommodation accommodation, CreateAccommodationRequest request);
}
