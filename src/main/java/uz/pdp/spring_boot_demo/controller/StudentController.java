package uz.pdp.spring_boot_demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.spring_boot_demo.entity.Student;
import uz.pdp.spring_boot_demo.repository.StudentRepository;

import java.util.List;

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

}
