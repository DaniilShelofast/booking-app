package project.bookingservice.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bookingservice.dto.payment.PaymentRequestDto;
import project.bookingservice.dto.payment.PaymentResponseDto;
import project.bookingservice.dto.payment.PaymentSearchParametersDto;
import project.bookingservice.dto.stripe.SessionRequestDto;
import project.bookingservice.dto.stripe.StripeResponseDto;
import project.bookingservice.exception.EntityNotFoundException;
import project.bookingservice.mapper.PaymentMapper;
import project.bookingservice.model.*;
import project.bookingservice.repository.BookingRepository;
import project.bookingservice.repository.PaymentRepository;
import project.bookingservice.repository.payment.PaymentSearchUserSpecificationProvider;
import project.bookingservice.repository.payment.PaymentSpecificationBuilder;
import project.bookingservice.service.PaymentService;
import project.bookingservice.service.StripeService;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;


@Transactional
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final BookingRepository bookingRepository;
    private final StripeService stripeService;
    private final PaymentSpecificationBuilder specificationBuilder;
    private final PaymentSearchUserSpecificationProvider paymentSearchUserSpecificationProvider;

    // Test commit check
    // todo getPaymentsByUser мені взагалі не подобається
    @Override
    public List<PaymentResponseDto> getPaymentsByUser(PaymentSearchParametersDto parametersDto) {
        User user = getUser();
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName() == RoleName.ROLE_ADMIN);

        Specification<Payment> build = specificationBuilder.build(parametersDto);
        if (!isAdmin) {
            build = build.and(paymentSearchUserSpecificationProvider.getSpecification(user.getId()));
        }
        return paymentRepository.findAll(build)
                .stream()
                .map(paymentMapper::toDto)
                .sorted(Comparator.comparing(PaymentResponseDto::getStatus))
                .toList();
    }

    @Override
    public String processSuccessfulPayment(String sessionId) {
        Session session;
        long expiresAt = Instant.now().plus(24, ChronoUnit.HOURS).getEpochSecond();
        try {
            session = Session.retrieve(sessionId);
            session.setExpiresAt(expiresAt);
        } catch (StripeException e) {
            throw new SessionException("Can't retrieve session: " + sessionId, e);
        }

        Payment payment = paymentRepository.findBySessionId(session.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find payment by session id " + sessionId));

        if (payment.getStatus() == Status.CONFIRMED || payment.getStatus() == Status.CANCELED || payment.getStatus() == Status.EXPIRED) {
            throw new RuntimeException("");
        }

        Booking booking = payment.getBooking();
        if ("paid".equalsIgnoreCase(session.getPaymentStatus())) {
            payment.setStatus(Status.CONFIRMED);
            booking.setStatus(Status.CONFIRMED);
        }

        paymentRepository.save(payment);
        bookingRepository.save(booking);
        return "Payment successful! Your reservation has been confirmed.";
    }

    @Override
    public String processCancelledPayment(String sessionId) {
        return "Payment suspended. You can continue it later.";
    }

    @Override
    public PaymentResponseDto createStripeSession(PaymentRequestDto requestDto) {
        Booking booking = bookingRepository.findById(requestDto.getBookingId()).orElseThrow(() -> new EntityNotFoundException("Can't find booking by id " + requestDto.getBookingId()));

        if (booking.getStatus() != Status.PENDING) {
            throw new IllegalStateException("Can't create payment for booking " + booking.getId());
        }

        Payment payment = paymentMapper.toModel(requestDto);
        BigDecimal totalAmount = calculateTotalAmount(booking);
        SessionRequestDto sessionRequest = buildSessionRequest(booking, getUser());
        StripeResponseDto stripeResponse = stripeService.createSession(sessionRequest);

        payment.setStatus(Status.PENDING);
        payment.setBooking(booking);
        payment.setSessionUrl(stripeResponse.sessionUrl());
        payment.setSessionId(stripeResponse.sessionId());
        payment.setAmountToPay(totalAmount);
        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private BigDecimal calculateTotalAmount(Booking booking) {
        long days = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());
        return booking.getAccommodation().getDailyRate().multiply(BigDecimal.valueOf(days));
    }

    private SessionRequestDto buildSessionRequest(Booking booking, User user) {
        return SessionRequestDto.builder().bookingId(booking.getId()).amount(calculateTotalAmount(booking))
                .currency("USD").productName(booking.getAccommodation().getType().name()).email(user.getEmail())
                .username(user.getUsername()).checkIn(booking.getCheckIn()).checkOut(booking.getCheckOut()).build();
    }
}
