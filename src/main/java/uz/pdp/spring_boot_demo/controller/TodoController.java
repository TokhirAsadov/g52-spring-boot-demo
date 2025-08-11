package uz.pdp.spring_boot_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.spring_boot_demo.dto.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*@Controller
@ResponseBody*/
@RestController
public class TodoController {

    List<Todo> todos = new ArrayList<>();

    @GetMapping(
            value = "/todos",
            consumes = "application/json",// content-type bilan javob olish mumkin
            produces = {"application/xml", "application/json"}
    )
    public List<Todo> getTodo(){
        todos.add(new Todo(UUID.randomUUID(),"salom todo"));
        todos.add(new Todo(UUID.randomUUID(),"alik todo"));
        System.out.println("+++++++++++++++++=");
        return todos;
    }

    /*@GetMapping("/todo/{id}/{name}")
    public Todo getTodoById(
            @PathVariable(name = "id") UUID todoId,
            @PathVariable(name = "name") String name
            ){
        System.out.println(name);
        return todos.stream().filter(todo -> todo.getId().equals(todoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }*/

    @GetMapping("/todo/{id}")
    public Todo getTodoById(
            @PathVariable(name = "id") UUID todoId
    ){
        return todos.stream().filter(todo -> todo.getId().equals(todoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }


// PathVariable
}
