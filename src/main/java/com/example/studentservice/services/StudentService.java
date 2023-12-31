package com.example.studentservice.services;

import java.beans.PropertyDescriptor;
import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.studentservice.dto.AddressDto;
import com.example.studentservice.models.Student;
import com.example.studentservice.repositories.StudentRepository;

@Service
public class StudentService {
  private final StudentRepository studentRepository;
  private final ApiClient apiClient;

  public StudentService(StudentRepository studentRepository, ApiClient apiClient){
    this.studentRepository = studentRepository;
    this.apiClient = apiClient;
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

  public boolean deleteById(Long id){
    // now use a hack to get user id first after that found better way
    Student student = getById(id);
    if (student != null){
      studentRepository.deleteById(id);
      return true;
    }
    return false;
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

  public boolean isRecordCreated(Long id) {
    return studentRepository.existsById(id);
  }

  public List<AddressDto> getStudentAddresses(Long studentId) {
    String url = "http://ADDRESS-SERVICE/student_addresses";
    HashMap<String, Object> options = new HashMap<>();
    String queryParams = "student_id=" + studentId;
    options.put("queryParams", queryParams);

    List<AddressDto> addressList = apiClient.getList(url, AddressDto[].class, options);
    return addressList;
  }

  public List<AddressDto> createStudentAddresses(Long studentId, List<AddressDto> addresses) {
    String url = "http://ADDRESS-SERVICE/addresses/bulk";
    // inject student id
    for (AddressDto address : addresses) {
      address.setStudentId(studentId);
    }

    List<AddressDto> addressList = apiClient.postList(url, AddressDto[].class, addresses);
    return addressList;
  }

  public AddressDto createStudentAddress(AddressDto addressDto) {
    String url = "http://ADDRESS-SERVICE/addresses";
    AddressDto responseAddress = apiClient.post(url, AddressDto.class, addressDto);
    return responseAddress;
  }

  public AddressDto updateAddress(AddressDto addressDto) {
    String url = "http://ADDRESS-SERVICE/addresses/"+addressDto.getId();
    AddressDto responseAddress = apiClient.patch(url, AddressDto.class, addressDto);
    return responseAddress;
  }

  public String deleteAddress(AddressDto addressDto) {
    String url = "http://ADDRESS-SERVICE/addresses/"+addressDto.getId();
    String response = apiClient.delete(url, String.class);
    return response;
  }
}
