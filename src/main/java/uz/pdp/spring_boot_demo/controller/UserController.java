package uz.pdp.spring_boot_demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.spring_boot_demo.dto.IUserDTO;
import uz.pdp.spring_boot_demo.dto.UserCreator;
import uz.pdp.spring_boot_demo.entity.User;
import uz.pdp.spring_boot_demo.repository.UserRepository;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<EntityModel<IUserDTO>> findById(@PathVariable Long id){
        IUserDTO data = userRepository.getUserById(id);
        EntityModel<IUserDTO> model = EntityModel.of(data);
        model.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).findAll()).withRel("users"));
        model.add(linkTo(methodOn(UserPhotoController.class).download(data.getUserPhotoId(),null)).withRel("user_photo").expand().withName("GET"));
        model.add(linkTo(methodOn(UserController.class).create(new UserCreator("xxx xxx","xxx@gmail.com"))).withRel("create").expand().withName("POST"));
        model.add(linkTo(methodOn(AttachmentController.class).download(UUID.randomUUID(),null)).withRel("download-attachment").expand().withName("GET"));
        return ResponseEntity.ok(model);
    }
}
