package com.example.studentservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentservice.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
  
}
