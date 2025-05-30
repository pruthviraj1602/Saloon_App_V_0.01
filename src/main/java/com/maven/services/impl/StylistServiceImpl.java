package com.maven.services.impl;

import com.maven.Repository.StylistRepository;
import com.maven.models.Stylist;
import com.maven.models.dtos.StylistDto;
import com.maven.services.StylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StylistServiceImpl implements StylistService {

    @Autowired
    StylistRepository stylistRepository;
    @Override
    public Stylist addStylist(Stylist stylist) {
        return stylistRepository.save(stylist);
    }

    @Override
    public Stylist updateStylist(Stylist stylist) throws IOException {
        return null;
    }

    @Override
    public String deleteStylist(Long teamMemberId) {
        stylistRepository.deleteById(teamMemberId);

        if(stylistRepository.findById(teamMemberId).isEmpty()){
            return "Stylist deleted";
        }
        else {
            return "Stylist not deleted";
        }
    }

    @Override
    public List<StylistDto> getAllStylist() {
        List<StylistDto> stylistDtos = new ArrayList<>();
        stylistRepository.findAll().forEach(stylist -> {
            StylistDto stylistDto = new StylistDto();
            stylistDto.setStylistId(stylist.getStylistId());
            stylistDto.setStylistName(stylist.getStylistName());
            stylistDto.setEmail(stylist.getEmail());
            stylistDto.setContact(stylist.getContact());
            stylistDto.setLocation(stylist.getLocation());
            stylistDto.setReference(stylist.getReference());
            stylistDtos.add(stylistDto);
        });
        return stylistDtos;
    }

    public Stylist getStylistById(Long id) {
        return stylistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stylist not found with ID: " + id));
    }

    @Override
    public Boolean updateIsActive(Long stylistId) {
        Optional<Stylist> stylist = stylistRepository.findById(stylistId);
        if (stylist.isPresent()) {
            Stylist stylist1 = stylist.get();
            stylist1.setActive(!stylist1.isActive());
            stylistRepository.save(stylist1);
            return true;
        }
        return false;
    }

}