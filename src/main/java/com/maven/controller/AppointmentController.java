package com.maven.controller;

import com.maven.exception.TimeSlotUnavailableException;
import com.maven.models.Appointment;
import com.maven.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

@RestController
@RequestMapping("/appointments")
@CrossOrigin("*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(
            @RequestParam("service") String service,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("stylist") String stylistName,
            @RequestParam("customerName") String customerName,
            @RequestParam("customerContact") String customerContact,
            @RequestParam("status") String status) {

        LocalDate appointmentDate;
        LocalTime appointmentTime;
        try {
            appointmentDate = LocalDate.parse(date);
            appointmentTime = LocalTime.parse(time);

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date or time format. Use yyyy-MM-dd and HH:mm:ss.");
        }

        Appointment appointment = new Appointment();
        appointment.setService(service);
        appointment.setDate(appointmentDate);
        appointment.setTime(appointmentTime);
        appointment.setStylist(stylistName);
        appointment.setCustomerName(customerName);
        appointment.setCustomerContact(customerContact);
        appointment.setStatus(status);
        try {
            Appointment booked = appointmentService.bookingAppointment(appointment);
            return ResponseEntity.ok("Appointment booked successfully for " +
                    booked.getDate() + " at " + booked.getTime() + ".");
        } catch (TimeSlotUnavailableException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @PutMapping("/cancel")
    public ResponseEntity<?> cancelAppointment(@RequestParam("id") Long id) {
        try {
            Appointment cancelled = appointmentService.cancelAppointment(id);
            return ResponseEntity.ok("Appointment with ID " + cancelled.getId() + " cancelled successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Appointment not found with ID: " + id);
        }
    }


    @PutMapping("/reschedule")
    public ResponseEntity<?> rescheduleAppointment(
            @RequestParam("id") Long id,
            @RequestParam("newDate") String newDate,
            @RequestParam("newTime") String newTime) {
        try {
            LocalDate date = LocalDate.parse(newDate);     // format: yyyy-mm-dd
            LocalTime time = LocalTime.parse(newTime);     // format: hh:mm:ms

            Appointment rescheduled = appointmentService.rescheduleAppointment(id, date, time);

            return ResponseEntity.ok("Appointment rescheduled to " +
                    rescheduled.getDate() + " at " + rescheduled.getTime());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Appointment not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during rescheduling: " + e.getMessage());
        }
    }


    @GetMapping("/availability")
    public ResponseEntity<?> checkAvailability(
            @RequestParam("service") String service,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("stylist") String stylist) {

        try {
            LocalDate parsedDate = LocalDate.parse(date);       // format: yyyy-mm-dd
            LocalTime parsedTime = LocalTime.parse(time);       // format: hh:mm:ms

            boolean available = appointmentService.checkAvailability(service, parsedDate, parsedTime, stylist);

            return ResponseEntity.ok(available ? "Slot is available." : "Slot is already booked.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid date/time format. Use yyyy-MM-dd and HH:mm:ss");
        }
    }

    @GetMapping("/total-count")
    public ResponseEntity<?> getTotalAppointmentsCount() {
        long count = appointmentService.countAllAppointments();
        return ResponseEntity.ok(Map.of("totalAppointments", count));
    }
}
