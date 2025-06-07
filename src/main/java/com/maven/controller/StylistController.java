package com.maven.controller;

import com.maven.models.Stylist;
import com.maven.services.StylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/stylist")
@CrossOrigin("*")
public class StylistController {

    @Autowired
    StylistService stylistService;


    @PostMapping("/add-stylist")
    public ResponseEntity<?> addStylist(
            @RequestParam("stylistName") String stylistName,
            @RequestParam("email") String email,
            @RequestParam("contact") String contact,
            @RequestParam("location") String location,
            @RequestParam("reference") String reference,
            @RequestParam("expert_in") String expertIn
          ) {

        Stylist stylist = new Stylist();
        stylist.setStylistName(stylistName);
        stylist.setEmail(email);
        stylist.setContact(contact);
        stylist.setLocation(location);
        stylist.setReference(reference);
        stylist.setExpert_in(expertIn); // Assuming 'expertIn' maps to specialization

        stylist.setDate(LocalDate.now());
        stylist.setActive(true);

        return ResponseEntity.ok(stylistService.addStylist(stylist));
    }


    @PutMapping("/update-stylist")
    public ResponseEntity<?> updateStylist(
            @RequestParam("stylistId") Long stylistId,
            @RequestParam("stylistName") String stylistName,
            @RequestParam("email") String email,
            @RequestParam("contact") String contact,
            @RequestParam("location") String location,
            @RequestParam("reference") String reference,
            @RequestParam("expert_in") String expertIn,
            @RequestParam("date") String date,
            @RequestParam("bio") String bio) {

        Stylist stylist = new Stylist();
        stylist.setStylistId(stylistId);              // Preserving existing ID
        stylist.setStylistName(stylistName);
        stylist.setEmail(email);
        stylist.setContact(contact);
        stylist.setLocation(location);
        stylist.setReference(reference);
        stylist.setExpert_in(expertIn);               // Same as in addStylist

        LocalDate localDate = LocalDate.parse(date);  // Convert String to LocalDate
//        stylist.setDate(String.valueOf(localDate));   // Save as String or update as LocalDate if the entity allows

        stylist.setActive(true);

        return ResponseEntity.ok(stylistService.addStylist(stylist));
    }


    @DeleteMapping("/delete-stylist")
    public ResponseEntity<?> deleteTeamMember(@RequestParam Long stylistId) {
        return ResponseEntity.ok(stylistService.deleteStylist(stylistId));
    }

    @GetMapping("/get-all-stylist")
    public ResponseEntity<?> getAllTeamMember() {
        return ResponseEntity.ok(stylistService.getAllStylist());
    }

    @GetMapping("/get-stylist")
    public ResponseEntity<?> getStylistById(@RequestParam("id") Long id) {
        Stylist stylist = stylistService.getStylistById(id);
        return ResponseEntity.ok(stylist);
    }

    @PutMapping("/update-is-active")
    public ResponseEntity<?> updateStatus(@RequestParam("id") Long stylistId){
        return ResponseEntity.ok(stylistService.updateIsActive(stylistId));
    }
}