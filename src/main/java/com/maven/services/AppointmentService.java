package com.maven.services;

import com.maven.models.Appointment;

import java.util.List;
import java.util.Map;

public interface AppointmentService {

    Appointment addAppointment(Appointment appointment);

    List<Appointment> getAppointment();

    Appointment getAppointment(Long id);

    Boolean deleteAppointment(Long id);

    Appointment updateAppointment(Appointment appointment);

    Map<String, Map<String, Double>> getPayment();
}
