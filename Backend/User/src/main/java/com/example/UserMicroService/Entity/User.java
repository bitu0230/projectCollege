package com.example.UserMicroService.Entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;

import java.util.List;

@Document(collection = User.COLLECTION_NAME)
@Data
@ToString
public class User {
    public static final String COLLECTION_NAME = "user";
    private String userName;
    @Id
    private String userEmailId;
    @Encrypted
    private String userPassword;
    private List<OrderedItem> orderedItemList;
}
