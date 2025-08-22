package uz.pdp.spring_boot_demo.dto;

public record LoginRequest(
        String username,
        String password
) {
}
