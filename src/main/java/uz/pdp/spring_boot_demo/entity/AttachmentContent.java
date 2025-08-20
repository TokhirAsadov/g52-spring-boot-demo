package uz.pdp.spring_boot_demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "bytes")
@Entity
@Table(name = "attachment_content")
public class AttachmentContent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid default uuid_generate_v4()")
    private UUID id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, name = "bytes")
    private byte[] bytes;


    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id", referencedColumnName = "id", nullable = false)
    private Attachment attachment;

}
