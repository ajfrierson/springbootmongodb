package com.infosys.springbootmongodb.service;

import com.infosys.springbootmongodb.exception.TodoCollectionException;
import com.infosys.springbootmongodb.model.TodoDTO;

import javax.validation.ConstraintViolationException;

public interface TodoService {
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;
}
