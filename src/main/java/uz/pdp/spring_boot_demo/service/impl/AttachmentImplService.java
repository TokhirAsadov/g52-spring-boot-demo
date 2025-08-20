package uz.pdp.spring_boot_demo.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.spring_boot_demo.entity.Attachment;
import uz.pdp.spring_boot_demo.entity.AttachmentContent;
import uz.pdp.spring_boot_demo.repository.AttachmentContentRepository;
import uz.pdp.spring_boot_demo.repository.AttachmentRepository;
import uz.pdp.spring_boot_demo.service.AttachmentService;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentImplService implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    public AttachmentImplService(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }

    @Override
    @Transactional
    public void fileUploadToDatabase(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file!=null) {
                boolean exists = attachmentRepository.existsByOriginalName(file.getOriginalFilename());
                if (exists){
                    throw new RuntimeException("File with name "+file.getOriginalFilename()+" already exists");
                }
                Attachment attachment = Attachment.builder()
                        .originalName(file.getOriginalFilename())
                        .size(file.getSize())
                        .contentType(file.getContentType())
                        .fileName(UUID.randomUUID().toString())
                        .build();
                Attachment saved = attachmentRepository.save(attachment);
                AttachmentContent attachmentContent = AttachmentContent.builder()
                        .attachment(saved)
                        .bytes(file.getBytes())
                        .build();
                attachmentContentRepository.save(attachmentContent);
            }
        }
    }

    @Override
    public ResponseEntity<String> downloadFileFromDatabaseById(UUID id, HttpServletResponse response) {
        if (attachmentRepository.existsById(id)) {
            Attachment attachment = attachmentRepository.findById(id).get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(id);
            if (optionalAttachmentContent.isPresent()) {
                AttachmentContent attachmentContent = optionalAttachmentContent.get();
                response.setContentType(attachment.getContentType());
                response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getOriginalName() + "\"");
                try {
                    response.getOutputStream().write(attachmentContent.getBytes());
                    response.flushBuffer();
                    return ResponseEntity.ok("File downloaded successfully");
                } catch (IOException e) {
                    return ResponseEntity.status(500)
                            .body("Error writing file to output stream: " + e.getMessage());
                }
            } else {
                return ResponseEntity.status(404)
                        .body("Attachment content not found for id: " + id);
            }
        }
        return ResponseEntity.status(404)
                .body("File not found with id: " + id);
    }
}
