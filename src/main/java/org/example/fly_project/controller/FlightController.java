package org.example.fly_project.controller;

import jakarta.validation.Valid;
import org.example.fly_project.dto.FlightCreateDTO;
import org.example.fly_project.dto.FlightResponseDTO;
import org.example.fly_project.model.Flight;
import org.example.fly_project.service.FlightService;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;
    private final ModelMapper modelMapper;

    public FlightController(FlightService flightService, ModelMapper modelMapper) {
        this.flightService = flightService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    @PreAuthorize("permitAll()")
    public ResponseEntity<FlightResponseDTO> createFlight(@Valid @RequestBody FlightCreateDTO dto) {
        Flight flight = flightService.createFlight(dto);
        return new ResponseEntity<>(modelMapper.map(flight, FlightResponseDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FlightResponseDTO>> search(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airlineName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime estDepartureTimeFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime estDepartureTimeTo) {
        return ResponseEntity.ok(flightService.search(flightNumber, airlineName, estDepartureTimeFrom, estDepartureTimeTo));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FlightResponseDTO> getFlight(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return ResponseEntity.ok(modelMapper.map(flight, FlightResponseDTO.class));
    }
}
