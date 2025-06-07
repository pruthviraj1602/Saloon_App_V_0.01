package com.maven.controller;

import com.maven.models.Appointment;
import com.maven.services.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;


    @PostMapping("/add-appointment")
    public ResponseEntity<?> addAppointment(@RequestBody Appointment appointment){
        return ResponseEntity.ok(appointmentService.addAppointment(appointment));
    }

    @GetMapping("/get-all-appointment")
    public ResponseEntity<?> getAllAppointment(){
        return ResponseEntity.ok(appointmentService.getAppointment());
    }

    @GetMapping("/get-appointment")
    public ResponseEntity<?> getAppointment(@RequestParam Long id){
        return ResponseEntity.ok(appointmentService.getAppointment(id));
    }

    @DeleteMapping("/delete-appointment")
    public ResponseEntity<?> deleteAppointment(@RequestParam Long id){
        return ResponseEntity.ok(appointmentService.deleteAppointment(id));
    }

    @PutMapping("/update-appointment")
    public ResponseEntity<?> updateAppointment(@RequestBody Appointment appointment){
        return ResponseEntity.ok(appointmentService.updateAppointment(appointment));
    }

    @GetMapping("/get-payment-summery")
    public ResponseEntity<?> getPaymentSummery(){
        return ResponseEntity.ok(appointmentService.getPayment());
    }

}
