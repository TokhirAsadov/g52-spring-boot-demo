package uz.pdp.spring_boot_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.spring_boot_demo.dto.IRoleUserDTO;
import uz.pdp.spring_boot_demo.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByNameOrCode(String name, String code);

    @Query("from Role where id in ?1")
    List<Role> getRolesByIds(Set<Long> ids);

    @Query(value = "select r.name, r.code from roles r" +
            " join users_roles ur on ur.roles_id=r.id where ur.user_id=?1;",nativeQuery = true)
    List<IRoleUserDTO> getRoleDtoForUser(Long userId);
}
