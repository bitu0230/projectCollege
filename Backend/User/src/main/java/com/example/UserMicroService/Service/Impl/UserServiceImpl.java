package com.example.UserMicroService.Service.Impl;

import com.example.UserMicroService.DTO.LoginDTO;
import com.example.UserMicroService.Entity.User;
import com.example.UserMicroService.Repository.UserRepository;
import com.example.UserMicroService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public Boolean addUserDetails(User user) {
        if(!userRepository.existsById(user.getUserEmailId())){
            userRepository.save(user);
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public List<User> findAll(){
        List<User> usersList = userRepository.findAll();
        return usersList;
    }
    @Override
    public void deleteId(String userId) {
        userRepository.deleteById(userId);
    }
    @Override
    public Optional<User> findOne(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void updateUser(String userId, User user) {
        Optional<User> studentOptional = userRepository.findById(userId);
        if(((Optional) studentOptional).isPresent()) {
            User studentData = studentOptional.get();
            studentData.setUserName(user.getUserName());
            studentData.setUserName(user.getUserName());
            studentData.setUserPassword(user.getUserPassword());
            studentData.setUserEmailId(user.getUserEmailId());
            userRepository.save(studentData);
        }
        else {
            throw new IllegalArgumentException("student not found");
        }

    }

    @Override
    public Boolean saveByUser(User user){
        userRepository.save(user);
        return true;
    }

    @Override
    public Boolean exists(String userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> userOptional=userRepository.findById(userId);
        User user=userOptional.get();
        return user;
    }


}
