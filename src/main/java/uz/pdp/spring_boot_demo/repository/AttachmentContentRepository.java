package uz.pdp.spring_boot_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.spring_boot_demo.entity.AttachmentContent;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {
    Optional<AttachmentContent> findByAttachmentId(UUID attachmentId);
}
