package DTOs;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FlightCreateDTO {
    @NotBlank @Size(max=6) @Pattern(regexp = "^[A-Z0-9]{1,6}$")
    private String flightNumber;
    @NotBlank private String airlineName;
    @NotNull private LocalDateTime departureTime;
    @NotNull private LocalDateTime arrivalTime;
    @Min(1) private Integer availableSeats;
}