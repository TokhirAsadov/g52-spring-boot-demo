package uz.pdp.spring_boot_demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.spring_boot_demo.dto.Todo;
import uz.pdp.spring_boot_demo.exceptions.DataNotFountException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//Customer
//id -int
//fullName
//age

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
    public ResponseEntity<List<Todo>> getTodo() {
//        todos.add(new Todo(UUID.randomUUID(), "salom todo"));
//        todos.add(new Todo(UUID.randomUUID(), "alik todo"));
        System.out.println("+++++++++++++++++=");
//        return new ResponseEntity<>(todos, HttpStatus.OK);
        return ResponseEntity.ok(todos);
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
    public ResponseEntity<Todo> getTodoById(
            @PathVariable(name = "id") UUID todoId
    ) {
        Todo todo1 = todos.stream().filter(todo -> todo.getId().equals(todoId))
                .findFirst()
                .orElseThrow(() -> new DataNotFountException("Todo not found"));
        return ResponseEntity.ok(todo1);
    }

    /*@ExceptionHandler(DataNotFountException.class)
    public final ResponseEntity<Object> error_404(DataNotFountException e, HttpServletRequest req){
        return ResponseEntity
                .status(404)
                .body(Map.of(
                        "error_message", e.getMessage(),
                        "error_code", 404,
                        "error_path", req.getRequestURI(),
                        "timestamp", LocalDateTime.now()

                ));
    }*/

    @PostMapping("/todo/create")
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Boolean> create(@RequestBody Todo todo) {
        todos.add(new Todo(UUID.randomUUID(),todo.getTitle()));
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }

    @PutMapping("/todo/update")
    public ResponseEntity<Boolean> update(@RequestBody Todo todo) {
        Todo oldTodo = todos.stream().filter(todo1 -> todo1.getId().equals(todo.getId()))
                .findFirst()
                .orElseThrow(() -> new DataNotFountException("Todo not fount"));
        oldTodo.setTitle(todo.getTitle());
        return ResponseEntity.status(200).body(true);
    }


// PathVariable
}
