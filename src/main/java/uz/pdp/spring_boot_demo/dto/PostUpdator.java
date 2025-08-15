package uz.pdp.spring_boot_demo.dto;

public record PostUpdator(
        String title,
        String body,
        Integer userId
) {
}
