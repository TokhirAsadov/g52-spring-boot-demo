package uz.pdp.spring_boot_demo.dto;

public record TokenRequest(
        String username,
        String password
) {
}
