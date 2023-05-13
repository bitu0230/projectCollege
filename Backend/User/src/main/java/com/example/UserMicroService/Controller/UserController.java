package com.example.UserMicroService.Controller;

import com.example.UserMicroService.DTO.LoginDTO;
import com.example.UserMicroService.DTO.OrderedItemDTO;
import com.example.UserMicroService.DTO.UserDTO;
import com.example.UserMicroService.Entity.OrderedItem;
import com.example.UserMicroService.Entity.User;
import com.example.UserMicroService.FeignInterface.FeignInterface;
import com.example.UserMicroService.Service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    FeignInterface feignInterface;
//When checking out , this function is invoked and it will send these parameters
    @PostMapping("/getOrderedItemsList")
    public ResponseEntity<Boolean> getOrderedItemsList(@RequestParam("userId") String userId,@RequestParam("merchantId") String merchantId,@RequestParam("productId") String productId,@RequestParam("productQuantity") Integer productQuantity, @RequestParam("productPrice") Double productPrice, @RequestParam("address") String address)
    {
        User user=userService.getUserById(userId);
        //Retrieving the previous orders
        List<OrderedItem> orderedItemList=user.getOrderedItemList();
        //A new DTO for adding new products
        OrderedItemDTO orderedItemDTO=new OrderedItemDTO();
        orderedItemDTO.setMerchantId(merchantId);
        orderedItemDTO.setProductId(productId);
        orderedItemDTO.setProductPrice(productPrice);
        orderedItemDTO.setProductQuantity(productQuantity);
        orderedItemDTO.setAddress(address);
        OrderedItem orderedItem= new OrderedItem();
        BeanUtils.copyProperties(orderedItemDTO,orderedItem);
        //pushing into the old ordered list
        orderedItemList.add(orderedItem);
        user.setOrderedItemList(orderedItemList);
        userService.saveByUser(user);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    @PostMapping("/addUsers")
    public ResponseEntity<Boolean> addUserDetails(@RequestBody UserDTO userDTO){
        User user= new User();
        BeanUtils.copyProperties(userDTO,user);
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
        user.setOrderedItemList(Collections.emptyList());
        Boolean userCreated = userService.addUserDetails(user);
        if(userCreated){
            String created=feignInterface.createCartById(userDTO);
        }
        if(userCreated){

        return new ResponseEntity<Boolean>(userCreated,HttpStatus.CREATED);}
        else{
            return new ResponseEntity<Boolean>(userCreated,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        String found="Error";
        if(userService.exists(loginDTO.getUserEmail())){
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            User user=userService.getUserById(loginDTO.getUserEmail());
            if(bCryptPasswordEncoder.matches(loginDTO.getUserPassword(),user.getUserPassword())){
                return new ResponseEntity<String>(user.getUserEmailId(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>(found,HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/showOrderHistory/{userId}")
    public ResponseEntity<List<OrderedItem>> showOrderHistory(@PathVariable("userId") String userId){
        User user=userService.getUserById(userId);
        List<OrderedItem> orderedItemList=user.getOrderedItemList();
        return new ResponseEntity<List<OrderedItem>>(orderedItemList,HttpStatus.OK);
    }


    @GetMapping("/findoneUser/{userId}")
    public ResponseEntity<Object> findOneUser(@PathVariable("userId") String userId){
        Optional<User> user = userService.findOne(userId);
        if(user.isPresent()){
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user.get(),userDTO);
            return new ResponseEntity<Object>(userDTO,HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Not Found",HttpStatus.NOT_FOUND);
    }
    @PostMapping("/updatePassword")
    public ResponseEntity<Boolean> updatePassword(@RequestBody LoginDTO loginDTO){
       User user=userService.getUserById(loginDTO.getUserEmail());
       BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
       user.setUserPassword(bCryptPasswordEncoder.encode(loginDTO.getUserPassword()));
       userService.saveByUser(user);
        return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }

    @PostMapping("/updateUsername/{userId}/{userUserName}")
    public ResponseEntity<Boolean> updateUsername(@PathVariable("userId") String userId, @PathVariable("userUserName") String userUserName){
        User user=userService.getUserById(userId);
        user.setUserName(userUserName);
        userService.saveByUser(user);
        return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }
//For Deleting both Cart and User
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("userId") String userId){
        Boolean deletedCart=feignInterface.deleteCartById(userId);
        userService.deleteId(userId);
        return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }

}

