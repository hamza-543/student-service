package com.example.studentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
  
  private Long id;
  private String zipcode;
  //should be a enum
  private String city;
  private String country;
}


