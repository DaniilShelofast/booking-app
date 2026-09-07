package project.bookingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bookingservice.dto.accommodation.AccommodationDto;
import project.bookingservice.dto.accommodation.CreateAccommodationRequest;
import project.bookingservice.exception.EntityNotFoundException;
import project.bookingservice.mapper.AccommodationMapper;
import project.bookingservice.model.Accommodation;
import project.bookingservice.repository.AccommodationRepository;
import project.bookingservice.service.AccommodationService;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;

    @Override
    public AccommodationDto createAccommodation(CreateAccommodationRequest request) {
        Accommodation accommodation = accommodationMapper.toEntity(request);
        accommodationRepository.save(accommodation);
        return accommodationMapper.toDto(accommodation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccommodationDto> getAllAccommodations(Pageable pageable) {
        return accommodationRepository.getAvailableAccommodations(pageable)
                .map(accommodationMapper::toDto);
    }

    @Override
    public AccommodationDto getAccommodationById(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find accommodation by id " + id));
        return accommodationMapper.toDto(accommodation);
    }

    @Override
    @Transactional()
    public AccommodationDto updateAccommodation(Long id, CreateAccommodationRequest request) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find accommodation by id " + id));
        accommodationMapper.updateAccommodation(accommodation, request);
        accommodationRepository.save(accommodation);
        return accommodationMapper.toDto(accommodation);
    }

    @Override
    public void deleteAccommodation(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find accommodation by id " + id));
        accommodationRepository.deleteById(accommodation.getId());
    }
}
