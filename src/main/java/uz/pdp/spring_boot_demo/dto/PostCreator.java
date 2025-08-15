package uz.pdp.spring_boot_demo.dto;

public record PostCreator(
        Integer id,
        String title,
        String body,
        Integer userId
) {
}
