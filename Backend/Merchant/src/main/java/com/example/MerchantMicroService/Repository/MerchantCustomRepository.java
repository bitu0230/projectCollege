package com.example.MerchantMicroService.Repository;

import com.example.MerchantMicroService.DTO.MerchantProductPriceDTO;
import com.example.MerchantMicroService.Entity.Merchant;
import com.example.MerchantMicroService.Entity.ProductInventory;

import java.util.List;

public interface MerchantCustomRepository {

    boolean saveProductInventoryByMerchantId(String merchantId, ProductInventory productInventory);
//    List<> getMerchantLists(String productId);
//    List<MerchantProductPriceDTO> fetchPriceStockByProductId(String productId);
}

