package com.example.UserMicroService.DTO;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString
public class UserDTO {

    private String userName;
    private String userEmailId;
    private String userPassword;

}
