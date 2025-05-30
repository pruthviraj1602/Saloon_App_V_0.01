package com.maven.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDto {

    private Long id;
    private String service;
    private LocalDate date;
    private LocalTime time;
    private String stylist;
    private String customerName;
    private String customerContact;
    private String status;

}
