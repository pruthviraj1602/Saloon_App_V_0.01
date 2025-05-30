package com.maven.services.impl;


import com.maven.Repository.AppointmentRepository;
import com.maven.exception.TimeSlotUnavailableException;
import com.maven.models.Appointment;
import com.maven.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;


    @Override
    public Appointment bookingAppointment(Appointment appointment) {
        boolean available = checkAvailability(
                appointment.getService(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getStylist()
        );

        if (!available) {
            throw new TimeSlotUnavailableException("The selected time slot is already booked.");
        }


        appointment.setEndTime(appointment.getTime().plusMinutes(30));

        return appointmentRepository.save(appointment);
    }


    @Override
    public Appointment cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus("cancelled");
        return appointmentRepository.save(appointment);
    }


    @Override
    public Appointment rescheduleAppointment(Long id, LocalDate newAppointmentDate, LocalTime newAppointmentTime) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setDate(newAppointmentDate);
        appointment.setTime(newAppointmentTime);
        appointment.setStatus("Rescheduled");

        return appointmentRepository.save(appointment);
    }



    public boolean checkAvailability(String service, LocalDate date, LocalTime time, String stylist) {
        LocalTime newEndTime = time.plusMinutes(30);
        List<Appointment> appointments = appointmentRepository.findByStylistAndDate(stylist, date);

        for (Appointment existing : appointments) {
            LocalTime existingStart = existing.getTime();
            LocalTime existingEnd = existingStart.plusMinutes(30);

            boolean overlap = time.isBefore(existingEnd) && newEndTime.isAfter(existingStart);
            if (overlap) {
                return false;
            }
        }

        return true;
    }

    @Override
    public long countAllAppointments() {
        return appointmentRepository.count();
    }


}

