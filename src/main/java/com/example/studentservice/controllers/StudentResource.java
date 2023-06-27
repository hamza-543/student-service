package com.example.studentservice.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.studentservice.models.Student;
import com.example.studentservice.services.StudentService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class StudentResource {

  private final StudentService userService;

  @GetMapping("/students")
  public List<Student> index(){
    return userService.allStudents();
  }
  
  @PostMapping("/students")
  public Student createUser(@RequestBody Student requestBody){
    return userService.createStudent(requestBody);
  }

  @GetMapping("/students/{id}")
  public Student get(@PathVariable String id){
    Student user =  userService.getById(Long.parseLong(id));
    if (user == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return user;
  }

  @DeleteMapping("/students/{id}")
  public String getUser(@PathVariable long id){
    userService.deleteById(id);
    return "User successfully deleted";
  }

  @PatchMapping("/students/{id}")
  public Student update(@PathVariable long id, @RequestBody Student user){
    return userService.update(id, user);
  }

}
