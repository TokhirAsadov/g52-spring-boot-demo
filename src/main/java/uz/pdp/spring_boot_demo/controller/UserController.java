package uz.pdp.spring_boot_demo.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.spring_boot_demo.dto.User;
import uz.pdp.spring_boot_demo.dto.UserCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/user")
public class UserController {
    AtomicInteger atomicInteger = new AtomicInteger(0);
    List<User> users = new ArrayList<>();

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody UserCreator creator){
        users.add(User.builder()
                        .id(atomicInteger.incrementAndGet())
                        .fullName(creator.fullName())
                        .username(creator.username())
                        .age(creator.age())
                .build());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll(){

        int a = 5 / 0;

        return ResponseEntity.ok(users);
    }
}
