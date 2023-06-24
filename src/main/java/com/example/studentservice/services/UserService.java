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

import com.example.studentservice.models.User;
import com.example.studentservice.repositories.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  public List<User> allUsers(){
    return userRepository.findAll();
  }

  public User getById(Long id){
    Optional <User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()){
      User user = optionalUser.get();
      return user;
    }
    return null;
  }

  public void deleteById(Long id){
    userRepository.deleteById(id);
  }

  public User update(Long id,User user){
    User existedUser = getById(id);
    if (existedUser == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    BeanUtils.copyProperties(user, existedUser, getNullPropertyNames(user));
    return userRepository.save(existedUser);
  }

  public User createUser(User user){
   return userRepository.save(user);
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
