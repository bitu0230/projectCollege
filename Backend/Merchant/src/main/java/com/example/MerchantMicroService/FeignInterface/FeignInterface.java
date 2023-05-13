package com.example.MerchantMicroService.FeignInterface;

import com.example.MerchantMicroService.DTO.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(url = "http://192.168.43.81:9090/Products", value = "Feign")
public interface FeignInterface {
    @GetMapping("/getAllProducts")
    List<ProductDTO> getAll();

    @PostMapping("/addProducts")
    String save(ProductDTO productDTO);

}
