package com.example.CartMicroService.services;

import com.example.CartMicroService.DTO.CartItemsDTO;
import com.example.CartMicroService.entity.Cart;

public interface CartService {
    public void createCart(String cartId);
    public Boolean addToCart(String userId,CartItemsDTO cartItemsDTO);
    public Cart getCart(String userId);
    public void updateCart(Cart cart);
    public Boolean remove(String userId,String merchantId,String productId);
    public Boolean deleteCartById(String cartId);
}
