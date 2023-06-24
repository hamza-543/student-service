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

import com.example.studentservice.models.User;
import com.example.studentservice.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserResource {

  private final UserService userService;

  @GetMapping("/users")
  public List<User> index(){
    return userService.allUsers();
  }
  
  @PostMapping("/users")
  public User createUser(@RequestBody User requestBody){
    return userService.createUser(requestBody);
  }

  @GetMapping("/users/{id}")
  public User get(@PathVariable String id){
    User user =  userService.getById(Long.parseLong(id));
    if (user == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return user;
  }

  @DeleteMapping("/users/{id}")
  public String getUser(@PathVariable long id){
    userService.deleteById(id);
    return "User successfully deleted";
  }

  @PatchMapping("/users/{id}")
  public User update(@PathVariable long id, @RequestBody User user){
    return userService.update(id, user);
  }

}
