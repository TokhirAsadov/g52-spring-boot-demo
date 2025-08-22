package uz.pdp.spring_boot_demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface IUserDTO {
    Long getId();
    @JsonProperty("full_name")
    String getFullName();
    String getEmail();

    @JsonIgnore
    @Value("#{@userPhotoRepository.getIdByUserId(target.id)}")
    UUID getUserPhotoId();

   // roles
}
