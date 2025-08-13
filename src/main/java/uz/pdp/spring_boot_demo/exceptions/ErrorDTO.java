package uz.pdp.spring_boot_demo.exceptions;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class ErrorDTO {
    private String errorPath;
    private Integer errorCode;
    private Object errorBody;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
