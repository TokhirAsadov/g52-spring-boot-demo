package uz.pdp.spring_boot_demo.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface IUserDTO {
    Long getId();
    String getFullName();
    String getUsername();

    @Value("#{@roleRepository.getRoleDtoForUser(target.id)}")
    List<IRoleUserDTO> getRoles();
}
