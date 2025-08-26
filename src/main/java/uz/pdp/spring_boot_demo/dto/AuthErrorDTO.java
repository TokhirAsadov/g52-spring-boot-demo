package uz.pdp.spring_boot_demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthErrorDTO {
    @JsonProperty("error_message")
    private String errorMessage;
    @JsonProperty("error_path")
    private String errorPath;
    @JsonProperty("error_code")
    private int errorCode;
    @JsonProperty("time")
    private LocalDateTime timestamp;

    public AuthErrorDTO(String errorMessage, String errorPath, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorPath = errorPath;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Tashkent")));
    }
}
