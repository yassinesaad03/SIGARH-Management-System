package org.telecom.sigarh.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.telecom.sigarh.services.UploadService;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(
            @RequestParam("photo") MultipartFile photo
    ) throws IOException {

        String fileName = uploadService.uploadImage(photo);

        return ResponseEntity.ok(fileName);

    }

}