package org.example.flight_project.service;

import org.example.flight_project.dto.FlightCreateDTO;
import org.example.flight_project.dto.FlightResponseDTO;
import org.example.flight_project.model.Flight;
import org.example.flight_project.repository.FlightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;

    public FlightService(FlightRepository flightRepository, ModelMapper modelMapper) {
        this.flightRepository = flightRepository;
        this.modelMapper = modelMapper;
    }

    public Flight createFlight(FlightCreateDTO dto) {
        if (flightRepository.findByFlightNumber(dto.getFlightNumber()).isPresent())
            throw new RuntimeException("Flight number already exists");
        if (!dto.getDepartureTime().isBefore(dto.getArrivalTime()))
            throw new RuntimeException("Departure must be before arrival");
        Flight flight = modelMapper.map(dto, Flight.class);
        return flightRepository.save(flight);
    }

    public List<FlightResponseDTO> search(String flightNumber, String airlineName, LocalDateTime from, LocalDateTime to) {
        List<Flight> flights;
        if (from != null && to != null) {
            flights = flightRepository.findByFlightNumberContainingIgnoreCaseAndAirlineNameContainingIgnoreCaseAndDepartureTimeBetween(
                    flightNumber == null ? "" : flightNumber,
                    airlineName == null ? "" : airlineName, from, to);
        } else {
            flights = flightRepository.findByFlightNumberContainingIgnoreCaseAndAirlineNameContainingIgnoreCase(
                    flightNumber == null ? "" : flightNumber,
                    airlineName == null ? "" : airlineName);
        }
        return flights.stream().map(f -> modelMapper.map(f, FlightResponseDTO.class)).collect(Collectors.toList());
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Flight not found"));
    }
}
