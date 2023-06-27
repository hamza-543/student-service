package com.example.studentservice.services;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.studentservice.models.Student;
import com.example.studentservice.repositories.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository){
    this.studentRepository = studentRepository;
  }

  public List<Student> allStudents(){
    return studentRepository.findAll();
  }

  public Student getById(Long id){
    Optional <Student> optionalStudent = studentRepository.findById(id);
    if (optionalStudent.isPresent()){
      Student student = optionalStudent.get();
      return student;
    }
    return null;
  }

  public void deleteById(Long id){
    studentRepository.deleteById(id);
  }

  public Student update(Long id,Student student){
    Student existedStudent = getById(id);
    if (existedStudent == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    BeanUtils.copyProperties(student, existedStudent, getNullPropertyNames(student));
    return studentRepository.save(existedStudent);
  }

  public Student createStudent(Student student){
   return studentRepository.save(student);
  }

  private String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> nullNames = new HashSet<>();
    for (PropertyDescriptor pd : pds) {
        Object srcValue = src.getPropertyValue(pd.getName());
        if (srcValue == null) {
            nullNames.add(pd.getName());
        }
    }
    // add id
    nullNames.add("id");

    String[] result = new String[nullNames.size()];
    return nullNames.toArray(result);
  }
}