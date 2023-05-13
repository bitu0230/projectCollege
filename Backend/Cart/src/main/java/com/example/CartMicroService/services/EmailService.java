package com.example.CartMicroService.services;

import com.example.CartMicroService.DTO.UserDTO;
import com.example.CartMicroService.entity.Email;

public interface EmailService {
    String sendSimpleMail(Email email, String userId,String body);
}
