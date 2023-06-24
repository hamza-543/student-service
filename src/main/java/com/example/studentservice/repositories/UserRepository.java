package com.example.studentservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentservice.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
  
}
