package com.infosys.springbootmongodb.controller;

import com.infosys.springbootmongodb.model.TodoDTO;
import com.infosys.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepo;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
        List<TodoDTO> todos = todoRepo.findAll();
        if(todos.size() > 0){
            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
        try {
             todo.setCreatedAt(new Date(System.currentTimeMillis()));
             todoRepo.save(todo);
             return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable("id") String id){
        Optional<TodoDTO> todo = todoRepo.findById(id);
        if(todo.isPresent()){
            return new ResponseEntity<>(todo, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No todo with id " +id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO todo){
            Optional<TodoDTO> todoOptional = todoRepo.findById(id);
            if(todoOptional.isPresent()){
                TodoDTO todoToSave = todoOptional.get();
                todoToSave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoToSave.getCompleted());
                todoToSave.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToSave.getTodo());
                todoToSave.setDescription(todo.getDescription() != null ? todo.getDescription() : todoToSave.getDescription());
                todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
                todoRepo.save(todoToSave);
                return new ResponseEntity<>(todoToSave, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Todo not found with id " + id, HttpStatus.NOT_FOUND);
            }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
        try {
            todoRepo.deleteById(id);
            return new ResponseEntity<>("Successfully deleted id " + id, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
