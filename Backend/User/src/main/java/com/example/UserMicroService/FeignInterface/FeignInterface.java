package com.example.UserMicroService.FeignInterface;

import com.example.UserMicroService.DTO.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://192.168.43.81:9999/cart", value = "FeignUser")

public interface FeignInterface {
    //when user is created , we are sending userDTO to cart and creating a cart with cartId as userId
    @PostMapping("/createCart")
    String createCartById(@RequestBody UserDTO userDTO);
    //when user is deleted , we are sending userId to cart for deleting cart
    @DeleteMapping("/deleteCart")
    Boolean deleteCartById(@RequestParam("cartId") String cartId);
}
