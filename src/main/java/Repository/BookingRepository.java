package org.example.flight_project.repository;

import org.example.flight_project.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.flight.departureTime < :endTime AND b.flight.arrivalTime > :startTime")
    List<Booking> findConflictingBookings(@Param("userId") Long userId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
