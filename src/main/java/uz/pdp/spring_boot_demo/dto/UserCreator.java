package uz.pdp.spring_boot_demo.dto;

import java.util.Set;

public record UserCreator(
        String fullName,
        String username,
        String password,
        Set<Long> rolesIds
) {
}
