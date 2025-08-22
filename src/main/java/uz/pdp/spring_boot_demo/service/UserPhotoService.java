package uz.pdp.spring_boot_demo.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface UserPhotoService {
    void create(Long userId, MultipartFile file) throws IOException;

    ResponseEntity<String> downloadUserPhoto(UUID id, HttpServletResponse response);
}
