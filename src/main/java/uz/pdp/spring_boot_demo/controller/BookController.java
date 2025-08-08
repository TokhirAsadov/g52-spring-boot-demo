package uz.pdp.spring_boot_demo.controller;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.spring_boot_demo.dto.BookCreateDTO;
import uz.pdp.spring_boot_demo.entity.Book;


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

    @PostMapping("/book/update")
    public String bookUpdate(@ModelAttribute Book update){
        String sql = "update books set title = ?, author = ? where id = ?";
        jdbcTemplate.update(sql, update.getTitle(), update.getAuthor(), update.getId());
        return "redirect:/";
    }

    @DeleteMapping("/book/delete/{id}")
    public String bookDelete(@PathVariable(name = "id") Integer id){
        // /book/delete/{id}
        // /book/delete/1
        // /book/delete/2
        // /book/delete/10

        String sql = "delete from books where id = ?";
        jdbcTemplate.update(sql, id);

        return "redirect:/";
    }

    @GetMapping("/book/detailsForUpdate/{id}")
    public String getBookDetailsForUpdating(@PathVariable(name = "id") Integer id, Model model){
        String sql = "select * from books where id = ?";
        var mapper = BeanPropertyRowMapper.newInstance(Book.class);
        Book book = jdbcTemplate.queryForObject(sql, mapper, id);
        model.addAttribute("book",book);
        System.out.println(book);
        return "book_update";
    }


}
