package uz.pdp.spring_boot_demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.spring_boot_demo.dto.IUserDTO;
import uz.pdp.spring_boot_demo.dto.UserCreator;
import uz.pdp.spring_boot_demo.entity.Role;
import uz.pdp.spring_boot_demo.entity.User;
import uz.pdp.spring_boot_demo.repository.RoleRepository;
import uz.pdp.spring_boot_demo.repository.UserRepository;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserCreator creator){
        boolean exists = userRepository.existsByUsername(creator.username());

        if (!exists){
            List<Role> rolesByIds = roleRepository.getRolesByIds(creator.rolesIds());
            User user = User.builder()
                    .fullName(creator.fullName())
                    .username(creator.username())
                    .password(creator.password())
                    .roles(rolesByIds)
                    .build();
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(creator.fullName()+" user is created✅✅✅");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists❌❌❌");
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<IUserDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(userRepository.getUserById(id));
    }
}
