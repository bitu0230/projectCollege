package com.example.CartMicroService.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    private String userName;
    private String userEmailId;
    private String userPassword;
}
