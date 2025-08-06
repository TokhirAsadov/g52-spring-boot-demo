package uz.pdp.spring_boot_demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uz.pdp.spring_boot_demo.dto.MailingConfigDTO;
import uz.pdp.spring_boot_demo.entity.Book;

import java.util.List;


@Controller
public class HomeController {

    @Value("${g52.message}")
    private String message;

    private final JdbcTemplate jdbcTemplate;

    private final MailingConfigDTO mailingConfigDTO;

    public HomeController(MailingConfigDTO mailingConfigDTO, JdbcTemplate jdbcTemplate) {
        this.mailingConfigDTO = mailingConfigDTO;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/")
    public String homePage(Model model){
        String sql = "select * from books;";
        List<Book> books = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class));
        model.addAttribute("books",books);
        return "home_page";
    }


}
