package com.example.studentservice.dto;

import java.util.List;

import com.example.studentservice.models.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentAddressDto {

  private Student student;
  private List<AddressDto> addresses;

}
