package com.example.recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;


@Slf4j
@RestController
@RequestMapping("/images")
public class ImageController {

    //Carpeta local donde encontrar imagenes en el get
    private final Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            // Seguridad básica: bloquear path traversal
            if (filename.contains("..")) {
                log.warn("GET /images/{} - invalid filename", filename);
                return ResponseEntity.badRequest().build();
            }

            Path filePath = uploadDir.resolve(filename).normalize();

            if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
                log.warn("GET /images/{} - file not found", filename);
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(filePath.toUri());

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            log.info("GET /images/{} - image served, contentType={}", filename, contentType);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (MalformedURLException e) {
            log.error("GET /images/{} - invalid URL", filename, e);
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            log.error("GET /images/{} - unexpected error", filename, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
         if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "File is empty"));
            }

            // Crear carpeta uploads si no existe
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String originalName = file.getOriginalFilename() == null ? "image" : file.getOriginalFilename();
            String safeName = originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String storedName = UUID.randomUUID() + "_" + safeName;

            Path target = uploadDir.resolve(storedName).normalize();
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            log.info("POST /images - uploaded file={} as {}", originalName, storedName);

            Map<String, String> body = new HashMap<>();
            body.put("fileName", storedName);
            body.put("url", "/images/" + storedName);
            return ResponseEntity.status(HttpStatus.CREATED).body(body);

        } catch (Exception e) {
            log.error("POST /images - upload failed", e);
            return ResponseEntity.internalServerError()
                .body(Map.of("message", "Could not upload image"));
        }
    }

}
