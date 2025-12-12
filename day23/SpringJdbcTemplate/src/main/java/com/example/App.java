package com.example;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.beans.Todo;
import com.example.config.JdbcConfig;
import com.example.dao.TodoDao;
import com.example.dao.TodoDaoImpl;

public class App 
{
    public static void main(String[] args)
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        TodoDao dao = context.getBean(TodoDaoImpl.class);

       
        Todo t1 = new Todo(1,"task1","task1 so and so");
        Todo t2 = new Todo(2,"task2","task2 so and so");
        Todo t3 = new Todo(3,"task3","task3 so and so");
        Todo t4 = new Todo(4,"task4","task4 so and so");

        dao.saveTodo(t1);
        dao.saveTodo(t2);
        dao.saveTodo(t3);
        dao.saveTodo(t4);

        
        Todo updatedTodo = new Todo(2,"task2","coding");
        dao.updateTodo(updatedTodo);

        
        System.out.println("Fetching ID 4:");
        System.out.println(dao.getTodo(4));   // prints todo with id=4

        
        dao.deleteTodo(4);

        
        
        
        System.out.println("\nAll Todos:");
        List<Todo> todos = dao.getAllTodos();
        for(Todo t : todos)
        {
            System.out.println(t);
        }
    }
}

