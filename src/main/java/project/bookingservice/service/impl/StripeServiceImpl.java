package project.bookingservice.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import project.bookingservice.dto.stripe.SessionRequestDto;
import project.bookingservice.dto.stripe.StripeResponseDto;
import project.bookingservice.mapper.StripeMapper;
import project.bookingservice.service.StripeService;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class StripeServiceImpl implements StripeService {
    private final StripeMapper stripeMapper;

    @Value("${app.domain}")
    private String domain;

    @Override
    public StripeResponseDto createSession(SessionRequestDto requestDto) {
        try {
            String successUrl = UriComponentsBuilder
                    .fromUriString(domain)
                    .path("/api/payments/success")
                    .queryParam("sessionId", "{CHECKOUT_SESSION_ID}")
                    .build(false)
                    .toUriString();

            String cancelUrl = UriComponentsBuilder
                    .fromUriString(domain)
                    .path("/api/payments/cancel")
                    .queryParam("sessionId", "{CHECKOUT_SESSION_ID}")
                    .build(false)
                    .toUriString();

            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                    .setName(requestDto.getUsername())
                    .setEmail(requestDto.getEmail())
                    .build();
            Customer customer = Customer.create(customerParams);

            SessionCreateParams sessionParams = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setCustomer(customer.getId())
                    .setSuccessUrl(successUrl)
                    .setCancelUrl(cancelUrl)
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(requestDto.getCurrency())
                                                    .setUnitAmount(requestDto.getAmount().multiply(BigDecimal.valueOf(100)).longValue())
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(requestDto.getProductName())
                                                                    .setDescription(requestDto.getCheckIn() + " - " + requestDto.getCheckOut())
                                                                    .build()
                                                    ).build()
                                    ).build()

                    ).build();

            Session session = Session.create(sessionParams);
            return stripeMapper.toDto(session);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe session", e);
        }
    }
}
