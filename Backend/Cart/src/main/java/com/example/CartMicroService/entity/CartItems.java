package com.example.CartMicroService.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartItems {
    private String merchantId;
    private String productId;
    private Integer productQuantity;
    private Double productPrice;
}
