package uz.pdp.spring_boot_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.spring_boot_demo.dto.IUserDTO;
import uz.pdp.spring_boot_demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    @Query(value = "select id, full_name, username from users where id = ?1 limit 1;", nativeQuery = true)
    IUserDTO getUserById(Long userId);
}
