package com.example.CartMicroService.feigninterface;
import com.example.CartMicroService.DTO.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url ="http://192.168.43.81:9091/User",value = "hui")
public interface FeignInterface {
    @PostMapping("/getOrderedItemsList")
    Boolean getOrderedItemsList(@RequestParam("userId") String userId,@RequestParam("merchantId") String merchantId,@RequestParam("productId") String productId,@RequestParam("productQuantity") Integer productQuantity, @RequestParam("productPrice") Double productPrice,@RequestParam("address") String address);
}
