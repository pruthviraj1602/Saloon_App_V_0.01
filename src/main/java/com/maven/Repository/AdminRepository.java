package com.maven.Repository;

import com.maven.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {


    Optional<Admin> findByUsername(String email);

    Optional<Admin> getAdminByUsername(String username);
}
