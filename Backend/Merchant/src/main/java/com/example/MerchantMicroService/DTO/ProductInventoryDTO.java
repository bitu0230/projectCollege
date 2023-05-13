package com.example.MerchantMicroService.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductInventoryDTO {
    private String productId;
    private Integer stock;
    private Double price;

}
