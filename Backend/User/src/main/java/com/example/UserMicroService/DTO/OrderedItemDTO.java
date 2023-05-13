package com.example.UserMicroService.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderedItemDTO {
    private String productId;
    private String merchantId;
    private Double productPrice;
    private Integer productQuantity ;
    private String address;
}


