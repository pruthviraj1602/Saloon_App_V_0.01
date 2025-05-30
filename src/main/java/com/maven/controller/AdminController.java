package com.maven.controller;

import com.maven.Repository.AdminRepository;
import com.maven.config.jwt.JwtUtils;
import com.maven.models.Admin;
import com.maven.models.dtos.jwtRequest;
import com.maven.models.dtos.jwtResponse;
import com.maven.services.AdminService;
import com.maven.services.jwt.MyUserDetailsService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;


@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    @PostConstruct
    public void createAdmin(){
        Admin admin = new Admin();
        admin.setName("Admin");
        admin.setUsername("admin@gmail.com");
        admin.setContact("1234567890");
        admin.setPassword(passwordEncoder.encode("123"));
        adminService.addUser(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody jwtRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect Username or Password.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        Optional<Admin> optionalUser = adminRepository.getAdminByUsername(userDetails.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails.getUsername());

        if(optionalUser.isPresent()){
            jwtResponse jwtResponse= new jwtResponse();
            jwtResponse.setUsername(optionalUser.get().getUsername());
            jwtResponse.setToken(jwt);
            return ResponseEntity.ok(jwtResponse);
        }
        return ResponseEntity.ok(null);
    }



    @GetMapping("/")
    public String session(HttpSession session){
        return "SESSION Id: "+session.getId();
    }


}
