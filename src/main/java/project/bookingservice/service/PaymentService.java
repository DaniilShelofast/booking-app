package project.bookingservice.service;

import com.stripe.exception.StripeException;
import project.bookingservice.dto.payment.PaymentRequestDto;
import project.bookingservice.dto.payment.PaymentResponseDto;
import project.bookingservice.dto.payment.PaymentSearchParametersDto;

import java.util.List;

public interface PaymentService {
    List<PaymentResponseDto> getPaymentsByUser(PaymentSearchParametersDto parametersDto);

    PaymentResponseDto createStripeSession(PaymentRequestDto requestDto);

    String processSuccessfulPayment(String sessionId) throws StripeException;

    String processCancelledPayment(String sessionId);
}
