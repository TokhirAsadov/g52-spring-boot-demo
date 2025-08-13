package uz.pdp.spring_boot_demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import uz.pdp.spring_boot_demo.entity.Student;
import uz.pdp.spring_boot_demo.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Student>> findAll(){
        List<Student> allStudents = studentRepository.findAll();
        return ResponseEntity.ok(allStudents);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Student> findById(@PathVariable Integer id){
        Optional<Student> optional = studentRepository.findById(id);
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Void> create(@RequestBody Student student){
        System.out.println(student);
        studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
