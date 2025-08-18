package uz.pdp.spring_boot_demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import uz.pdp.spring_boot_demo.dto.RoleCreator;
import uz.pdp.spring_boot_demo.entity.Role;
import uz.pdp.spring_boot_demo.repository.RoleRepository;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Void> create(@RequestBody RoleCreator creator){
        boolean exists = roleRepository.existsByNameOrCode(creator.name(), creator.code());
        if (!exists){
            Role role = Role.builder()
                    .name(creator.name())
                    .code(creator.code())
                    .build();
            roleRepository.save(role);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Role>> findAll(){
        return ResponseEntity.ok(roleRepository.findAll());
    }
}
