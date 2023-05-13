package com.example.UserMicroService.Entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderedItem {
    private String productId;
    private String merchantId;
    private Double productPrice;
    private Integer productQuantity ;
    private String address;
}
