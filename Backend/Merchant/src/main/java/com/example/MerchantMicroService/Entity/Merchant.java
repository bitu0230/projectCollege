package com.example.MerchantMicroService.Entity;

import lombok.Data;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;

import java.util.List;

@Document(collection = Merchant.COLLECTION_NAME)
@Data
@ToString
public class Merchant {
    public static final String COLLECTION_NAME = "Merchant";
    @Id
    private String merchantId;
    private String merchantName;
    private String merchantEmailId;
    @Encrypted
    private String merchantPassword;
    private List<ProductInventory> productInventoryList;


}
