package com.example.MerchantMicroService.DTO;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString
public class ProductDTO {


    private String productName;
    private String productColor;
    private String productRAM;
    private String productStorage;
    private String productBattery;
    private String productProcessor;
    private String productImg;

}
