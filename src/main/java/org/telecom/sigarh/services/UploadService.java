package org.telecom.sigarh.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class UploadService {

    private final String UPLOAD_DIR = "uploads/";

    public String uploadImage(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("Aucune image sélectionnée.");
        }

        String extension = "";

        String originalName = file.getOriginalFilename();

        if (originalName != null && originalName.contains(".")) {

            extension = originalName.substring(originalName.lastIndexOf("."));

        }

        String fileName = UUID.randomUUID() + extension;

        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {

            Files.createDirectories(uploadPath);

        }

        Files.copy(
                file.getInputStream(),
                uploadPath.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING
        );

        return fileName;
    }

}