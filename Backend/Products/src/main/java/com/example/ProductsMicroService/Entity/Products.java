package com.example.ProductsMicroService.Entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Products.COLLECTION_NAME)
@Data
@ToString
public class Products {
    public static final String COLLECTION_NAME = "Products";
    @Id
    private String productId;
    private String productName;
    private String productColor;
    private String productRAM;
    private String productStorage;
    private String productBattery;
    private String productProcessor;
    private String productImg;

}
