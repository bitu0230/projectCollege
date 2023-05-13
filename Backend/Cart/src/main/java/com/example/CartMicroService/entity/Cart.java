package com.example.CartMicroService.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = Cart.COLLECTION_NAME)
@Data
@ToString
public class Cart {
    public static final String COLLECTION_NAME="cart";
    @Id
    private String cartId;
    private List<CartItems> cartItemsList;
}
