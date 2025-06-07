package com.maven.Repository;

import com.maven.models.Appointment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByStylistStylistIdAndDateAndTime(Long id, String date, String time);

    @Transactional
    Integer deleteAppointmentById(Long id);




}
