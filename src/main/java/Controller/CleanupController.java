package org.example.flight_project.controller;

import org.example.flight_project.repository.BookingRepository;
import org.example.flight_project.repository.FlightRepository;
import org.example.flight_project.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cleanup")
public class CleanupController {
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;

    public CleanupController(UserRepository userRepository, FlightRepository flightRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
    }

    @DeleteMapping
    public ResponseEntity<String> cleanup() {
        bookingRepository.deleteAll();
        flightRepository.deleteAll();
        userRepository.deleteAll();
        return ResponseEntity.ok("Database cleaned");
    }
}
