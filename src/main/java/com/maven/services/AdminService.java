package com.maven.services;

import com.maven.models.Admin;

public interface AdminService {

    String verify(String email, String password);

    Admin addUser(Admin admin);
}
