package org.example.fly_project.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private org.example.fly_project.model.User user;
    @ManyToOne
    private org.example.fly_project.model.Flight flight;
    private LocalDateTime bookingDate;
    private String status; // CONFIRMED, CANCELLED
}