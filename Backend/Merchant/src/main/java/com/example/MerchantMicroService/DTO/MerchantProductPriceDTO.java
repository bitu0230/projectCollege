package com.example.MerchantMicroService.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MerchantProductPriceDTO {
    private String merchantId;
    private Double productPrice;
    private Integer productStock;
}
