package com.example.ProductsMicroService.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductIdDTO {

    private String productId;
    private String productName;
    private String productColor;
    private String productRAM;
    private String productStorage;
    private String productBattery;
    private String productProcessor;
    private String productImg;
}
