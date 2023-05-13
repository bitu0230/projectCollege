package com.example.UserMicroService.Service;

import com.example.UserMicroService.DTO.LoginDTO;
import com.example.UserMicroService.Entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Boolean addUserDetails(User user);
    List<User> findAll();
    void deleteId(String userId);
    Optional<User> findOne(String userId);
    void updateUser(String userId, User user);
    Boolean saveByUser(User user);
    Boolean exists(String userId);
    User getUserById(String userId);
}
