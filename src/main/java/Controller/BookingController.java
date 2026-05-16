package org.example.flight_project.controller;

import jakarta.validation.Valid;
import org.example.flight_project.dto.BookingRequestDTO;
import org.example.flight_project.dto.BookingResponseDTO;
import org.example.flight_project.model.User;
import org.example.flight_project.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookingResponseDTO> bookFlight(@AuthenticationPrincipal User user, @Valid @RequestBody BookingRequestDTO request) {
        return ResponseEntity.ok(bookingService.bookFlight(user.getId(), request.getFlightId()));
    }

    @GetMapping("/book/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }
}
