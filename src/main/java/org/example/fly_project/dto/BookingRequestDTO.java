package org.example.fly_project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequestDTO {
    @NotNull private Long flightId;
}
