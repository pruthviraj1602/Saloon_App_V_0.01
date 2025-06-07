package com.maven.Repository;

import com.maven.models.Stylist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StylistRepository extends JpaRepository<Stylist, Long> {

    @Transactional
    Integer deleteStylistByStylistId(Long stylishId);
}
