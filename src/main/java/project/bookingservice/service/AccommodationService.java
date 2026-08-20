package project.bookingservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.bookingservice.dto.accommodation.AccommodationDto;
import project.bookingservice.dto.accommodation.CreateAccommodationRequest;

public interface AccommodationService {
    AccommodationDto createAccommodation(CreateAccommodationRequest request);

    Page<AccommodationDto> getAllAccommodations(Pageable pageable);

    AccommodationDto getAccommodationById(Long id);

    AccommodationDto updateAccommodation(Long id, CreateAccommodationRequest request);

    void deleteAccommodation(Long id);
}
