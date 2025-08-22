package uz.pdp.spring_boot_demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserCreator(
        @JsonProperty("full_name")
        String fullName,
        String email
) {
}
