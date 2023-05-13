package com.example.CartMicroService.controller;

import com.example.CartMicroService.DTO.*;
import com.example.CartMicroService.entity.Cart;
import com.example.CartMicroService.entity.CartItems;
import com.example.CartMicroService.entity.Email;
import com.example.CartMicroService.feigninterface.FeignInterface;
import com.example.CartMicroService.feigninterface.FeignMerchantInterface;
import com.example.CartMicroService.services.CartService;
import com.example.CartMicroService.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequestMapping("/cart")
@RestController
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    FeignInterface feignInterface;

    @Autowired
    FeignMerchantInterface feignMerchantInterface;

    @Autowired
    private EmailService emailService;
// when user is created , this cart with cartId as userId is created
    @PostMapping("/createCart")
    public ResponseEntity<String> createCartById(@RequestBody UserDTO userDTO){
        cartService.createCart(userDTO.getUserEmailId());
        return new ResponseEntity<String>("done",HttpStatus.OK);
    }

    @PostMapping("/addToCart/{userId}/{merchantId}/{productId}/{productPrice}")
    public ResponseEntity<Boolean> addToCart(@PathVariable("userId") String userId,@PathVariable("merchantId") String merchantId,@PathVariable("productId") String productId,@PathVariable("productPrice") Double productPrice){
        CartItemsDTO cartItemsDTO=new CartItemsDTO();
        cartItemsDTO.setProductQuantity(1);
        cartItemsDTO.setProductId(productId);
        cartItemsDTO.setMerchantId(merchantId);
        cartItemsDTO.setProductPrice(productPrice);
        Boolean added=cartService.addToCart(userId,cartItemsDTO);
        return new ResponseEntity<Boolean>(added,HttpStatus.OK);
    }

    @GetMapping(value = "/showCart/{userId}")
    public ResponseEntity<Cart> showCart(@PathVariable("userId") String userId){
        Cart cart=cartService.getCart(userId);
        return new ResponseEntity<>(cart,HttpStatus.ACCEPTED);
    }

    //To increase the quantity in the cart
    @PostMapping("/incrementProductCount/{userId}/{productId}/{merchantId}")
    public ResponseEntity<Integer> incrementProductCount(@PathVariable("userId") String userId,@PathVariable("merchantId") String merchantId,@PathVariable("productId") String productId){
        Cart cart=cartService.getCart(userId);
        Integer i=0;
        //First we will get the cart list and iterate through it to get the product with that product id and merchant id, and increase quantity
        List<CartItems> cartItemsList=cart.getCartItemsList();
        for(CartItems cartItems:cartItemsList){
            if((cartItems.getMerchantId().equals(merchantId)) && cartItems.getProductId().equals(productId)){
                i=cartItems.getProductQuantity()+1;
                cartItems.setProductQuantity(i);
                break;
            }
        }
        cartService.updateCart(cart);
        return new ResponseEntity<Integer>(i,HttpStatus.OK);
    }

    @PostMapping("/decrementProductCount/{userId}/{productId}/{merchantId}")
    public ResponseEntity<Integer> decrementProductCount(@PathVariable("userId") String userId,@PathVariable("merchantId") String merchantId,@PathVariable("productId") String productId){
        Cart cart=cartService.getCart(userId);
        List<CartItems> cartItemsList=cart.getCartItemsList();
        Integer i=0;
        for(CartItems cartItems:cartItemsList){
            if((cartItems.getMerchantId().equals(merchantId)) && cartItems.getProductId().equals(productId)){
                // Making sure the quantity does not go negative
                i=cartItems.getProductQuantity();
                if(i>1){
                    i=i-1;
                    cartItems.setProductQuantity(i);
                    break;
                }
            }
        }
        cartService.updateCart(cart);
        return new ResponseEntity<Integer>(i,HttpStatus.OK);
    }

    @PostMapping("/removeProductFromCart/{userId}/{productId}/{merchantId}")
    public ResponseEntity<Boolean> removeProductFromCart(@PathVariable("userId") String userId,@PathVariable("merchantId") String merchantId,@PathVariable("productId") String productId){
        Boolean deleted=cartService.remove(userId,merchantId,productId);
        return new ResponseEntity<Boolean>(deleted,HttpStatus.OK);
    }
// when we are in the cart page
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody CheckOutDTO checkOutDTO, Model model){
        String userId=checkOutDTO.getUserId();
        Cart cart=cartService.getCart(userId);
        List<CartItems> cartItemsList=cart.getCartItemsList();
        String available="done checkout";
        String body="Dear "+userId+",\n\nThank you for shopping at Something. We're delighted to hear that you’ve ordered the following products:\n\n";
        if(cartItemsList.size()==0){
            return new ResponseEntity<String>("Cart is empty",HttpStatus.BAD_REQUEST);
        }

        //validation:- sending a merchantdto to merchant services and getting the stock to compare
        for(CartItems cartItems:cartItemsList){
            MerchantCartDTO merchantCartDTO=new MerchantCartDTO();
            Integer qty=cartItems.getProductQuantity();
            String merchantId=cartItems.getMerchantId();
            String productId=cartItems.getProductId();
            merchantCartDTO.setMerchantId(merchantId);
            merchantCartDTO.setProductId(productId);
//            Integer stock=feignMerchantInterface.showStock(merchantCartDTO);
            //here
//            List<String> details=feignMerchantInterface.validate(merchantCartDTO);
//            String merchantName=details.get(0);
//            String productName=details.get(2);
//            Integer stock=Integer.parseInt(details.get(1));
//            body+=cartItems.getProductQuantity()+" "+productName+" from merchant "+merchantName+"\n";
            if (!feignMerchantInterface.validate(merchantCartDTO.getMerchantId(),merchantCartDTO.getProductId(),qty)){

                available=cartItems.getProductId()+ " " +cartItems.getProductQuantity() + " quantity is not available. Only "+ " are available";
                return new ResponseEntity<String>(available,HttpStatus.OK);
            }
        }
        body+="\nPlease note that if you have any questions or concerns about your order, please contact us at 9999999999.\n\n" +
                "Thank you for shopping with us!!";
        //if quantity available then we will reduce stock and send product to order history
        for(CartItems cartItems:cartItemsList){
            MerchantCartDTO merchantCartDTO=new MerchantCartDTO();
            merchantCartDTO.setMerchantId(cartItems.getMerchantId());
            merchantCartDTO.setProductId(cartItems.getProductId());
            merchantCartDTO.setProductQuantity(cartItems.getProductQuantity());
            //here
            Integer reduce=feignMerchantInterface.reduceStock(merchantCartDTO.getMerchantId(),merchantCartDTO.getProductId(),merchantCartDTO.getProductQuantity());
            Boolean done=feignInterface.getOrderedItemsList(checkOutDTO.getUserId(),cartItems.getMerchantId(),cartItems.getProductId(),cartItems.getProductQuantity(),cartItems.getProductPrice(),checkOutDTO.getAddress());
        }

        //sending mail and clearing cart
        Email email= new Email();
        emailService.sendSimpleMail(email, checkOutDTO.getUserId(),body);
        cartService.createCart(checkOutDTO.getUserId());
        return new ResponseEntity<String>(available,HttpStatus.OK);
    }

    //when we are in the product display page

    @PostMapping("/buyNow")
    public ResponseEntity<String> buyNow(@RequestBody BuyNowDTO buyNowDTO){
        String available="Done  Buying";
        String userId=buyNowDTO.getUserId();
        String body="Dear "+userId+",\n\nThank you for shopping at Something. We're delighted to hear that you’ve ordered the following products:\n\n";
        MerchantCartDTO merchantCartDTO=new MerchantCartDTO();
        merchantCartDTO.setMerchantId(buyNowDTO.getMerchantId());
        merchantCartDTO.setProductId(buyNowDTO.getProductId());
        merchantCartDTO.setProductQuantity(1);
        //here
//        List<String> details=feignMerchantInterface.details(merchantCartDTO);
//        System.out.println(details);
//        String merchantName=details.get(0);
//        String productName=details.get(2);
//        Integer stock=Integer.parseInt(details.get(1));
//        body+="1 "+productName+" from merchant "+merchantName+"\n";
        if(!feignMerchantInterface.validate(merchantCartDTO.getMerchantId(),merchantCartDTO.getProductId(),merchantCartDTO.getProductQuantity())){
            available="Stock not available";
            return new ResponseEntity<String>(available,HttpStatus.OK);
        }
        //Feign call for merchant service
        // hereeee
        Integer reduce=feignMerchantInterface.reduceStock(merchantCartDTO.getMerchantId(),merchantCartDTO.getProductId(),merchantCartDTO.getProductQuantity());
        // Feign call for user to update orderedItem
        Boolean done=feignInterface.getOrderedItemsList(buyNowDTO.getUserId(),buyNowDTO.getMerchantId(),buyNowDTO.getProductId(),1,buyNowDTO.getProductPrice(),buyNowDTO.getAddress());
        Email email=new Email();
        body+="\nPlease note that if you have any questions or concerns about your order, please contact us at 9999999999.\n\n" +
                "Thank you for shopping with us!!";
        emailService.sendSimpleMail(email,buyNowDTO.getUserId(),body);
        cartService.createCart(buyNowDTO.getUserId());
        return new ResponseEntity<String>(available,HttpStatus.OK);
    }

    //When we delete the user it deletes the cart
    @DeleteMapping("/deleteCart")
    public Boolean deleteCartById(@RequestParam("cartId") String cartId){
        return cartService.deleteCartById(cartId);
    }

    @GetMapping("/totalCost")
    public Double totalCost(@RequestParam("userId") String userId){
        Cart cart=cartService.getCart(userId);
        Double cost=0.0;
        List<CartItems> cartItemsList=cart.getCartItemsList();
        for(CartItems cartItems:cartItemsList){
            cost+=cartItems.getProductPrice()*cartItems.getProductQuantity();
        }
        return cost;
    }
}
