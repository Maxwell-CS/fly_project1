package DTOs;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {
    private Long id;
    private Long userId;
    private String userFullName;
    private Long flightId;
    private String flightNumber;
    private LocalDateTime bookingDate;
    private String status;
}