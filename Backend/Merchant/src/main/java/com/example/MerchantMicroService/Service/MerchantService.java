package com.example.MerchantMicroService.Service;

import com.example.MerchantMicroService.DTO.MerchantProductPriceDTO;
import com.example.MerchantMicroService.Entity.Merchant;
import com.example.MerchantMicroService.Entity.ProductInventory;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface MerchantService {

    public void addProductInventoryByMerchantId(String merchantId, ProductInventory productInventory);


    Merchant addMerchantDetails(String email,String password,String  name);
    List<Merchant> findAll();
    void deleteId(String merchantId);
    Optional<Merchant> findOne(String merchantId);
    Merchant updateMerchant(String merchantId, Merchant merchant);

    int editProductStock(String MerchantId,Integer qty,String ProductId);

    int getProductStock(String merchant,String productId);


    Boolean validation(String merchantId,String product,Integer qty);
    public List<MerchantProductPriceDTO> showPriceMerchantStock(String pid);
}
