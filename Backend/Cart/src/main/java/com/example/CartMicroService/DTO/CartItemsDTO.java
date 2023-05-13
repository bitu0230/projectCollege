package com.example.CartMicroService.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartItemsDTO {
    private String merchantId;
    private String productId;
    private Integer productQuantity;
    private Double productPrice;
}
