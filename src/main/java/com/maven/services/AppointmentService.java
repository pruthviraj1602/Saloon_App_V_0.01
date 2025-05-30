package com.maven.services;

import com.maven.models.Appointment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;


@Service
public interface AppointmentService  {


    Appointment bookingAppointment (Appointment appointment);

    Appointment cancelAppointment(Long id);

    Appointment rescheduleAppointment(Long id, LocalDate newAppointmentDate, LocalTime newAppointmentTime);

    boolean checkAvailability(String service, LocalDate date, LocalTime time, String stylist);

    long countAllAppointments();


}

