package com.maven.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String service;
    private String date;
    private String time;
    private String customerName;
    private String customerContact;
//    private String status;
    private Double payment;
    private String paymentType;

    @ManyToOne
    @JoinColumn(name = "stylist_id")
    private Stylist stylist;



}
