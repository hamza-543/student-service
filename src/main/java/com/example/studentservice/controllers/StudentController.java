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

public class StudentController {

  private final StudentService studentService;
  private WebClient.Builder webClientBuilder;

  @GetMapping("/students")
  public List<Student> index(){
    return studentService.allStudents();
  }
  
  @PostMapping("/students")
  public StudentAddressDto createStudent(@RequestBody StudentAddressDto requestBody){
    Student student = studentService.createStudent(requestBody.getStudent());
    if (student == null){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    List<AddressDto> addressList = studentService.createStudentAddresses(student.getId(), requestBody.getAddresses());
    StudentAddressDto studentAddressDto = new StudentAddressDto(student, addressList);
    return studentAddressDto;
  }

  @GetMapping("/students/{id}")
  public StudentAddressDto get(@PathVariable String id){
    Student student = studentService.getById(Long.parseLong(id));
    List<AddressDto> addressList = studentService.getStudentAddresses(1l);
    StudentAddressDto studentAddressDto = new StudentAddressDto(student, addressList);
    if (student == null || studentAddressDto == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return studentAddressDto;
  }

  @DeleteMapping("/students/{id}")
  public String getStudent(@PathVariable long id){
    studentService.deleteById(id);
    return "Student successfully deleted";
  }

  @PatchMapping("/students/{id}")
  public Student update(@PathVariable long id, @RequestBody Student student){
    return studentService.update(id, student);
  }

}
