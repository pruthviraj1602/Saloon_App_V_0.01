package com.maven.services.impl;

import com.maven.Repository.AppointmentRepository;
import com.maven.Repository.StylistRepository;
import com.maven.models.Appointment;
import com.maven.models.Stylist;
import com.maven.services.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final StylistRepository stylistRepository;

    @Override
    public Appointment addAppointment(Appointment appointment) {
        Optional<Stylist> stylist = stylistRepository.findById(appointment.getStylist().getStylistId());
        boolean exists = appointmentRepository.existsByStylistStylistIdAndDateAndTime(
                stylist.get().getStylistId(),
                appointment.getDate(),
                appointment.getTime()
        );
        if (exists) {
            return null;
        }

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointment() {
        return appointmentRepository.findAll();
    }

    @Override
    public Boolean deleteAppointment(Long id) {
        return appointmentRepository.deleteAppointmentById(id) != 0;
    }

    @Override
    public Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id).get();
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        Optional<Stylist> stylist = stylistRepository.findById(appointment.getStylist().getStylistId());
        boolean exists = appointmentRepository.existsByStylistStylistIdAndDateAndTime(
                stylist.get().getStylistId(),
                appointment.getDate(),
                appointment.getTime()
        );
        if (exists) {
            return null;
        }
        return appointmentRepository.save(appointment);
    }

    @Override
    public Map<String, Map<String, Double>> getPayment() {
        List<Appointment> appointments = appointmentRepository.findAll();

        return appointments.stream()
                .filter(a -> a.getPayment() != null && a.getPaymentType() != null && a.getDate() != null)
                .map(a -> {
                    String frequency = determineFrequency(a.getDate());
                    return frequency.equals("unknown") ? null : new AbstractMap.SimpleEntry<>(a, frequency);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getValue, // frequency
                        Collectors.groupingBy(
                                e -> e.getKey().getPaymentType(),
                                Collectors.summingDouble(e -> {
                                    try {
                                        return e.getKey().getPayment();
                                    } catch (NumberFormatException ex) {
                                        return 0.0;
                                    }
                                })
                        )
                ));

    }
    private String determineFrequency(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            LocalDate today = LocalDate.now();
            return date.equals(today) ? "daily" : "monthly";
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + dateStr);
            return "unknown";
        }

    }
}