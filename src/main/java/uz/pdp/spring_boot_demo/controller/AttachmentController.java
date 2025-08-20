package uz.pdp.spring_boot_demo.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.spring_boot_demo.service.AttachmentService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/attachment")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService service;

    // Bir nechta faylni yuklash uchun
    @PostMapping("/upload")
    public ResponseEntity<Void> upload(MultipartHttpServletRequest request){
        try {
            service.fileUploadToDatabase(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Faqat bitta faylni yuklash uchun
    @PostMapping("/uploadSingleFile")
    public ResponseEntity<Void> uploadSingleFile(MultipartFile file){
        try {
            service.uploadSingleFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Faylni yuklab olish uchun
    @GetMapping("/download/{id}")
    public ResponseEntity<String> download(@PathVariable UUID id, HttpServletResponse response){
        return service.downloadFileFromDatabaseById(id, response);
    }

}
