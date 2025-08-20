package uz.pdp.spring_boot_demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.spring_boot_demo.dto.IUserDTO;
import uz.pdp.spring_boot_demo.dto.UserCreator;
import uz.pdp.spring_boot_demo.entity.User;
import uz.pdp.spring_boot_demo.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;


    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserCreator creator){
        boolean exists = userRepository.existsByEmail(creator.email());

        if (!exists){
            User user = User.builder()
                    .fullName(creator.fullName())
                    .email(creator.email())
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
