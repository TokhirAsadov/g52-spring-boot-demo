package uz.pdp.spring_boot_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.spring_boot_demo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
