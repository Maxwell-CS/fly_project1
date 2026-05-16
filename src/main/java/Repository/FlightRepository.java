package org.example.flight_project.repository;

import org.example.flight_project.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findByFlightNumberContainingIgnoreCaseAndAirlineNameContainingIgnoreCaseAndDepartureTimeBetween(
            String flightNumber, String airlineName, LocalDateTime from, LocalDateTime to);
    List<Flight> findByFlightNumberContainingIgnoreCaseAndAirlineNameContainingIgnoreCase(
            String flightNumber, String airlineName);
}
