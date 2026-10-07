package project.bookingservice.controller;

import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.bookingservice.dto.payment.PaymentRequestDto;
import project.bookingservice.dto.payment.PaymentResponseDto;
import project.bookingservice.dto.payment.PaymentSearchParametersDto;
import project.bookingservice.service.PaymentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam("sessionId") String sessionId) throws StripeException {
        return paymentService.processSuccessfulPayment(sessionId);
    }

    @GetMapping("/cancel")
    public String paymentCancel(@RequestParam("sessionId") String sessionId) {
        return paymentService.processCancelledPayment(sessionId);
    }

    @GetMapping
    public List<PaymentResponseDto> getPaymentsByUserId(@RequestParam PaymentSearchParametersDto parametersDto) {
        return paymentService.getPaymentsByUser(parametersDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDto createPaymentSession(@RequestBody @Valid PaymentRequestDto requestDto) {
        return paymentService.createStripeSession(requestDto);
    }
}
