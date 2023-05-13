package com.example.MerchantMicroService.Entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data@ToString

public class ProductInventory {

    private String productId;
    private Integer stock;
    private Double price;

}
