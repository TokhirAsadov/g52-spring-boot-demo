package uz.pdp.spring_boot_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.spring_boot_demo.entity.Attachment;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    boolean existsByOriginalName(String originalName);
}
