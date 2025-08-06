package uz.pdp.spring_boot_demo.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.pdp.spring_boot_demo.dto.BookCreateDTO;


@Controller
public class BookController {

    private final JdbcTemplate jdbcTemplate;

    public BookController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/book/create")
    public String bookCreatePage(){
        return "book_create";
    }

    @PostMapping("/book/create")
    public String bookCreate(@ModelAttribute BookCreateDTO dto){
        String sql = "insert into books(title, author) values (?, ?);";
        jdbcTemplate.update(sql,dto.getTitle(),dto.getAuthor());
        return "redirect:/";
    }


}
