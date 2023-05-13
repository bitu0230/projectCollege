package com.example.CartMicroService.DTO;

import com.example.CartMicroService.entity.CartItems;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CartDTO {
    private String cartId;
    private List<CartItems> cartItemsList;
}
