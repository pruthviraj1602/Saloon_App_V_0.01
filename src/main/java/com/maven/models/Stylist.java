package com.maven.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Stylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stylistId;
    private String stylistName;
    private String email;
    private String contact;
    private String location;
    private String reference;
    private String expert_in;
    private LocalDate date;
    private boolean isActive = true;
}