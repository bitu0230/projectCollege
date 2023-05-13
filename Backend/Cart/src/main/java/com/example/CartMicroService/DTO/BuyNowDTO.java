package com.example.CartMicroService.DTO;

import lombok.Data;

@Data
public class BuyNowDTO {
    private String userId;
    private String productId;
    private String merchantId;
    private Double productPrice;
    private String address;
}
