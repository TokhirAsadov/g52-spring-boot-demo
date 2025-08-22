package uz.pdp.spring_boot_demo.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.spring_boot_demo.service.UserPhotoService;
import uz.pdp.spring_boot_demo.utils.ApiUrls;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(UserPhotoController.BASE_URL)
public class UserPhotoController {
    public static final String BASE_URL = ApiUrls.BASE_URL + "/user-photo";
    private final UserPhotoService userPhotoService;

    public UserPhotoController(UserPhotoService userPhotoService) {
        this.userPhotoService = userPhotoService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Void> create(@PathVariable Long userId, MultipartFile file) throws IOException {
        userPhotoService.create(userId, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<String> download(@PathVariable UUID id, HttpServletResponse response){
        return userPhotoService.downloadUserPhoto(id, response);
    }
}
