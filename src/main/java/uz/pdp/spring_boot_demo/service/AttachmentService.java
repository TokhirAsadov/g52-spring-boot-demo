package uz.pdp.spring_boot_demo.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.UUID;

public interface AttachmentService {
    void fileUploadToDatabase(MultipartHttpServletRequest request) throws IOException;
    ResponseEntity<String> downloadFileFromDatabaseById(UUID id, HttpServletResponse response);
    void uploadSingleFile(MultipartFile file) throws IOException;
}
