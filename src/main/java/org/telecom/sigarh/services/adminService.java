package org.telecom.sigarh.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.telecom.sigarh.dto.RegisterRequest;
import org.telecom.sigarh.entities.Admin;
import org.telecom.sigarh.repositories.adminRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class adminService {
    private final adminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void register(RegisterRequest request) {

        if (adminRepository.findByMatriculeAdmin(request.getMatriculeAdmin()).isPresent()) {

            throw new IllegalArgumentException(
                    "Ce matricule  est déjà utilisé."
            );

        }

        if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Cette adresse email est déjà utilisée.");
        }

        Admin admin = new Admin();

        admin.setNom(request.getNom());
        admin.setPrenom(request.getPrenom());
        admin.setMatriculeAdmin(request.getMatriculeAdmin());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        adminRepository.save(admin);

    }

    public void login(Long matriculeAdmin, String password) {

        Admin admin = adminRepository.findByMatriculeAdmin(matriculeAdmin)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Matricule  ou mot de passe incorrect."
                        )
                );

        if (!passwordEncoder.matches(password, admin.getPassword())) {

            throw new IllegalArgumentException(
                    "Matricule  ou mot de passe incorrect."
            );

        }

    }
}

