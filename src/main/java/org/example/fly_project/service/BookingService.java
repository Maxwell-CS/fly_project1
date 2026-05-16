package org.example.fly_project.service;

import org.example.fly_project.dto.BookingResponseDTO;
import org.example.fly_project.model.Booking;
import org.example.fly_project.model.Flight;
import org.example.fly_project.model.User;
import org.example.fly_project.repository.BookingRepository;
import org.example.fly_project.repository.FlightRepository;
import org.example.fly_project.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, FlightRepository flightRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    public BookingResponseDTO bookFlight(Long userId, Long flightId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getDepartureTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Cannot book a flight that has already departed");
        if (flight.getArrivalTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Flight already arrived");
        if (flight.getAvailableSeats() <= 0)
            throw new RuntimeException("No available seats");

        LocalDateTime newStart = flight.getDepartureTime();
        LocalDateTime newEnd = flight.getArrivalTime();
        boolean conflict = bookingRepository.findConflictingBookings(userId, newStart, newEnd).stream()
                .anyMatch(b -> !b.getId().equals(flightId));
        if (conflict)
            throw new RuntimeException("Booking conflicts with another flight's schedule");

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED");
        Booking saved = bookingRepository.save(booking);

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        generateEmailFile(saved);

        BookingResponseDTO response = new BookingResponseDTO();
        response.setId(saved.getId());
        response.setUserId(user.getId());
        response.setUserFullName(user.getFirstName() + " " + user.getLastName());
        response.setFlightId(flight.getId());
        response.setFlightNumber(flight.getFlightNumber());
        response.setBookingDate(saved.getBookingDate());
        response.setStatus(saved.getStatus());
        return response;
    }

    private void generateEmailFile(Booking booking) {
        String filename = "flight_booking_email_" + booking.getId() + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Nombres: " + booking.getUser().getFirstName() + " " + booking.getUser().getLastName() + "\n");
            writer.write("Número de vuelo: " + booking.getFlight().getFlightNumber() + "\n");
            writer.write("Fechas (ISO8601):\n");
            writer.write("Salida: " + booking.getFlight().getDepartureTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\n");
            writer.write("Llegada: " + booking.getFlight().getArrivalTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BookingResponseDTO getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        BookingResponseDTO response = new BookingResponseDTO();
        response.setId(booking.getId());
        response.setUserId(booking.getUser().getId());
        response.setUserFullName(booking.getUser().getFirstName() + " " + booking.getUser().getLastName());
        response.setFlightId(booking.getFlight().getId());
        response.setFlightNumber(booking.getFlight().getFlightNumber());
        response.setBookingDate(booking.getBookingDate());
        response.setStatus(booking.getStatus());
        return response;
    }
}
