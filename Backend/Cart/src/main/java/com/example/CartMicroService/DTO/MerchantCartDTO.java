package com.example.CartMicroService.DTO;

import lombok.Data;

@Data
public class MerchantCartDTO {
    private String merchantId;
    private String productId;
    private Integer productQuantity;
}
