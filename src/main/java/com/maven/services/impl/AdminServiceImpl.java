package com.maven.services.impl;

import com.maven.Repository.AdminRepository;
import com.maven.models.Admin;
import com.maven.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;


    @Override
    public String verify(String email, String password) {
        return null;
    }

    @Override
    public Admin addUser(Admin admin) {

        if(adminRepository.findByUsername(admin.getUsername()).isEmpty()){
            return adminRepository.save(admin);
        }else{
            return null;
        }
    }
}
