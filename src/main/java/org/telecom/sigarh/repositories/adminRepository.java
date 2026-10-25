package org.telecom.sigarh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telecom.sigarh.entities.Admin;

import java.util.Optional;

public interface adminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findByMatriculeAdmin(Long matriculeAdmin);

    Optional<Admin> findByEmail(String email);
}


