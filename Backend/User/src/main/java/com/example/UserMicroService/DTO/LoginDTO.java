package com.example.UserMicroService.DTO;

import lombok.Data;

@Data
public class LoginDTO {
    String userEmail;
    String userPassword;
}
