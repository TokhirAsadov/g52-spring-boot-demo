package uz.pdp.spring_boot_demo.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.spring_boot_demo.entity.Attachment;
import uz.pdp.spring_boot_demo.entity.AttachmentContent;
import uz.pdp.spring_boot_demo.entity.User;
import uz.pdp.spring_boot_demo.entity.UserPhoto;
import uz.pdp.spring_boot_demo.repository.UserPhotoRepository;
import uz.pdp.spring_boot_demo.repository.UserRepository;
import uz.pdp.spring_boot_demo.service.UserPhotoService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

// @Service = @Component
@Service
public class UserPhotoImplService implements UserPhotoService {
    private final UserPhotoRepository userPhotoRepository;
    private final UserRepository userRepository;

    public UserPhotoImplService(UserPhotoRepository userPhotoRepository, UserRepository userRepository) {
        this.userPhotoRepository = userPhotoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(Long userId, MultipartFile file) throws IOException {
        if (userRepository.existsById(userId)) {
            Optional<User> optionalUser = userRepository.findById(userId);
            User user = optionalUser.get();
            UserPhoto userPhoto = UserPhoto.builder().user(user).contentType(file.getContentType()).bytes(file.getBytes()).build();
            userPhotoRepository.save(userPhoto);
            System.out.println("User photo saved successfully for user id: " + userId);
        } else {
            throw new RuntimeException("User not found by id: " + userId);
        }
    }

    @Override
    public ResponseEntity<String> downloadUserPhoto(UUID id, HttpServletResponse response) {
        if (userPhotoRepository.existsById(id)) {
            Optional<UserPhoto> optionalUserPhoto = userPhotoRepository.findById(id);
            UserPhoto attachment = optionalUserPhoto.get();
            response.setContentType(attachment.getContentType());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + UUID.randomUUID() + "\"");
            try {
                response.getOutputStream().write(attachment.getBytes());
                response.flushBuffer();
                return ResponseEntity.ok("User Photo downloaded successfully");
            } catch (IOException e) {
                return ResponseEntity.status(500).body("Error writing file to output stream: " + e.getMessage());
            }

        }
        return ResponseEntity.status(404).body("File not found with id: " + id);
    }
}
