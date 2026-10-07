package project.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.bookingservice.config.MapperConfig;
import project.bookingservice.dto.payment.PaymentRequestDto;
import project.bookingservice.dto.payment.PaymentResponseDto;
import project.bookingservice.dto.payment.UpdateStatusDto;
import project.bookingservice.model.Payment;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    @Mapping(target = "bookingId", source = "booking.id")
    PaymentResponseDto toDto(Payment payment);

    @Mapping(target = "booking.id", source = "bookingId")
    Payment toModel(PaymentRequestDto requestDto);

    void updateStatus(@MappingTarget Payment payment, UpdateStatusDto requestDto);
}
