package uz.pdp.spring_boot_demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UserCreator(
        @NotBlank(message = "FullName bo`sh bo`lmasligi kerak!")
        @Size(min = 5, max = 20, message = "5 < FullName.length < 20")
        String fullName,
        String username,
        @Positive(message = "age > 0 bo`lishi shart") Integer age
) {
}
