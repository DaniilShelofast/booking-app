package project.bookingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import project.bookingservice.dto.accommodation.AccommodationDto;
import project.bookingservice.dto.accommodation.CreateAccommodationRequest;
import project.bookingservice.service.AccommodationService;

@RestController
@RequestMapping("/accommodations")
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    @PostMapping
    public AccommodationDto createAccommodation(@RequestBody @Valid CreateAccommodationRequest request) {
        return accommodationService.createAccommodation(request);
    }

//    @GetMapping
//    public Page<AccommodationDto> getAllAccommodations(Pageable pageable) {
//        return accommodationService.getAllAccommodations(pageable);
//    }

    @GetMapping("/{id}")
    public AccommodationDto getAccommodationById(@PathVariable Long id) {
        return accommodationService.getAccommodationById(id);
    }

    @PutMapping("/{id}")
    public AccommodationDto updateAccommodation(@PathVariable Long id, @RequestBody @Valid CreateAccommodationRequest request) {
        return accommodationService.updateAccommodation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteAccommodation(@PathVariable Long id) {
        accommodationService.deleteAccommodation(id);
    }
}
