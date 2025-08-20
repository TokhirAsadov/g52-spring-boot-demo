package uz.pdp.spring_boot_demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid default uuid_generate_v4()")
    private UUID id;

    @Column(nullable = false, name = "original_name")
    private String originalName;

    @Column(nullable = false, name = "size")
    private Long size;

    @Column(nullable = false, name = "content_type")
    private String contentType;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "url")
    private String url;
}
