package org.example.flight_project.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FlightResponseDTO {
    private Long id;
    private String flightNumber;
    private String airlineName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer availableSeats;
}
