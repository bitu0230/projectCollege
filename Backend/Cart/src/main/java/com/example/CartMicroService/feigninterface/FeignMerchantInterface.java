package com.example.CartMicroService.feigninterface;

import com.example.CartMicroService.DTO.MerchantCartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(url ="http://192.168.43.81:9092/Merchant",value = "merch")
public interface FeignMerchantInterface {
    @PostMapping("/getStock")
    Integer showStock(@RequestBody MerchantCartDTO merchantCartDTO);

    @PostMapping("/editStock")
    Integer reduceStock(@RequestParam String merchantId,@RequestParam String productId,@RequestParam Integer qty);

    @GetMapping("/validate")
    Boolean validate(@RequestParam String merchantId,@RequestParam String productId,@RequestParam Integer qty);
}
