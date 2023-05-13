package com.example.MerchantMicroService.DTO;

import com.example.MerchantMicroService.Entity.ProductInventory;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Encrypted;

import java.util.List;

@Data@ToString
public class MerchantDTO {

    private String merchantId;
    private String merchantName;
    private String merchantEmailId;
    @Encrypted
    private String merchantPassword;
    private List<ProductInventory> productInventoryList;
}
