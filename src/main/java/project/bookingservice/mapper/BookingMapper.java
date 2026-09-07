package project.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.bookingservice.config.MapperConfig;
import project.bookingservice.dto.booking.BookingDto;
import project.bookingservice.dto.booking.CreateBookingRequestDto;
import project.bookingservice.dto.booking.UpdateBookingRequestDto;
import project.bookingservice.model.Booking;

@Mapper(config = MapperConfig.class)
public interface BookingMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "accommodationId", source = "accommodation.id")
    BookingDto toDto(Booking booking);

    Booking toModel(CreateBookingRequestDto createBookingRequestDto);

    @Mapping(target = "accommodation", ignore = true)
    void updateBooking(@MappingTarget Booking booking, UpdateBookingRequestDto requestDto);
}
