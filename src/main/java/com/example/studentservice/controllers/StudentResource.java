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
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.studentservice.dto.AddressDto;
import com.example.studentservice.dto.StudentAddressDto;
import com.example.studentservice.models.Student;
import com.example.studentservice.services.StudentService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor

public class StudentResource {

  private final StudentService userService;
  private WebClient.Builder webClientBuilder;

  @GetMapping("/students")
  public List<Student> index(){
    return userService.allStudents();
  }
  
  @PostMapping("/students")
  public StudentAddressDto createUser(@RequestBody StudentAddressDto requestBody){
    Student student = userService.createStudent(requestBody.getStudent());
    if (student == null){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    List<AddressDto> addressList = userService.createStudentAddresses(student.getId(), requestBody.getAddresses());
    StudentAddressDto studentAddressDto = new StudentAddressDto(student, addressList);
    return studentAddressDto;
  }

  @GetMapping("/students/{id}")
  public StudentAddressDto get(@PathVariable String id){
    Student user = userService.getById(Long.parseLong(id));
    List<AddressDto> addressList = userService.getStudentAddresses(1l);
    StudentAddressDto studentAddressDto = new StudentAddressDto(user, addressList);
    if (user == null || studentAddressDto == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return studentAddressDto;
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
