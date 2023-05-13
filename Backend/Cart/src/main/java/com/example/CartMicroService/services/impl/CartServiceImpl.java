package com.example.CartMicroService.services.impl;

import com.example.CartMicroService.DTO.CartDTO;
import com.example.CartMicroService.DTO.CartItemsDTO;
import com.example.CartMicroService.entity.Cart;
import com.example.CartMicroService.entity.CartItems;
import com.example.CartMicroService.repository.CartRepository;
import com.example.CartMicroService.services.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Override
    public void createCart(String cartId) {

        Cart cart=new Cart();
        cart.setCartId(cartId);
        cart.setCartItemsList(Collections.emptyList());
        System.out.println(cart);
        cartRepository.save(cart);
    }

    @Override
    public Boolean addToCart(String userId, CartItemsDTO cartItemsDTO) {
        Optional<Cart> OptionalUserCart=cartRepository.findById(userId);
        Cart cart=OptionalUserCart.get();
        //to get user's cart item list
        List<CartItems> userCartItemsList =cart.getCartItemsList();
        //Iterating through the cart to check if the product exists , if exist then increase the quantity
        for(CartItems cartItems:userCartItemsList){
            if((cartItems.getMerchantId().equals(cartItemsDTO.getMerchantId())) && (cartItems.getProductId().equals(cartItemsDTO.getProductId()))){
                cartItems.setProductQuantity(cartItems.getProductQuantity()+cartItemsDTO.getProductQuantity());
                cart.setCartItemsList(userCartItemsList);
                cartRepository.save(cart);
                return true;
            }
        }
        //If not exist we will add new product to cart and save it
        CartItems cartItems=new CartItems();
        BeanUtils.copyProperties(cartItemsDTO,cartItems);
        userCartItemsList.add(cartItems);
        cart.setCartItemsList(userCartItemsList);
        cartRepository.save(cart);
        return true;
    }

    @Override
    public Cart getCart(String userId) {
        Optional<Cart> cart=cartRepository.findById(userId);
        Cart newcart=cart.get();
        return newcart;
    }

    @Override
    public void updateCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Boolean remove(String userId, String merchantId, String productId) {
        Optional<Cart> optionalCart=cartRepository.findById(userId);
        Cart cart=optionalCart.get();
        List<CartItems> cartItemsList=cart.getCartItemsList();
        for(CartItems cartItems:cartItemsList){
            if((cartItems.getProductId().equals(productId)) && (cartItems.getMerchantId().equals(merchantId))){
                cartItemsList.remove(cartItems);
                cart.setCartItemsList(cartItemsList);
                cartRepository.save(cart);
                break;
            }
        }
        return true;
    }

    @Override
    public Boolean deleteCartById(String cartId) {
        cartRepository.deleteById(cartId);
        return true;
    }

}
