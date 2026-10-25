package org.telecom.sigarh.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telecom.sigarh.dto.RegisterRequest;
import org.telecom.sigarh.entities.Admin;
import org.telecom.sigarh.dto.LoginRequest;
import org.telecom.sigarh.services.adminService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final adminService adminService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {

        try {

            adminService.register(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Compte créé avec succès.");

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {

        try {

            adminService.login(
                    request.getMatriculeAdmin(),
                    request.getPassword()
            );

            return ResponseEntity.ok("Connexion réussie.");

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }

    }
}
