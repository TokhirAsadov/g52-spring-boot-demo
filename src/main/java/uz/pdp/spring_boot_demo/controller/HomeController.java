package uz.pdp.spring_boot_demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @GetMapping("/home")
    @PreAuthorize("hasRole('ADMIN')")
    public String home() {
        return "Welcome to the Home Page!";
    }

}
