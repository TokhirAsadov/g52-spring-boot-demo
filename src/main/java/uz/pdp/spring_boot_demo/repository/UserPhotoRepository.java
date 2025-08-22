package uz.pdp.spring_boot_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.spring_boot_demo.entity.UserPhoto;

import java.util.UUID;

public interface UserPhotoRepository extends JpaRepository<UserPhoto, UUID> {
    @Query("select u.id from UserPhoto u where u.user.id = ?1" )
    UUID getIdByUserId(Long userId);
}
