package com.example.MerchantMicroService.Controller;

import com.example.MerchantMicroService.DTO.MerchantDTO;
import com.example.MerchantMicroService.DTO.MerchantProductPriceDTO;
import com.example.MerchantMicroService.DTO.ProductDTO;
import com.example.MerchantMicroService.DTO.ProductInventoryDTO;
import com.example.MerchantMicroService.Entity.Merchant;
import com.example.MerchantMicroService.Entity.ProductInventory;
import com.example.MerchantMicroService.FeignInterface.FeignInterface;
import com.example.MerchantMicroService.Repository.MerchantRepository;
import com.example.MerchantMicroService.Service.MerchantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Merchant")
public class MerchantController {

    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    FeignInterface feignInterface;

    @Autowired
    MerchantService merchantService;

    @GetMapping("/feign")
    public List<ProductDTO> show()
    {

        return feignInterface.getAll();
    }


    @GetMapping("/addProductDTO")
    public String addProduct(@RequestParam String merchantId, @RequestParam String productName,
            @RequestParam String productColor,
                                             @RequestParam String productRAM,
                                             @RequestParam String productStorage,
                                             @RequestParam String productBattery,
                                             @RequestParam String productProcessor,
                                             @RequestParam String productImg,
                             @RequestParam Double price,
                             @RequestParam Integer stock


    )
    {
        ProductDTO productDTO = new ProductDTO();
        ProductInventoryDTO productInventoryDTO = new ProductInventoryDTO();

        productDTO.setProductName(productName);
        productDTO.setProductColor(productColor);
        productDTO.setProductRAM(productRAM);
        productDTO.setProductStorage(productStorage);
        productDTO.setProductBattery(productBattery);
        productDTO.setProductProcessor(productProcessor);
        productDTO.setProductImg(productImg);

        productInventoryDTO.setStock(stock);
        productInventoryDTO.setPrice(price);
        productInventoryDTO.setProductId(feignInterface.save(productDTO));

        ProductInventory productInventory = new ProductInventory();
        BeanUtils.copyProperties(productInventoryDTO, productInventory);
        merchantService.addProductInventoryByMerchantId(merchantId, productInventory);
//        System.out.println(productInventory.getStock());
//        Optional<Merchant> merchant=merchantService.findOne(merchantId);
///       productInventory.setProductId(feignInterface.save(productDTO));
//       // productInventory.getProductId();
//        ArrayList<ProductInventory> npi=new ArrayList<ProductInventory>();
//        List<ProductInventory> pi=merchant.get().getProductInventoryList();
//        pi.add(productInventory);
//        Merchant merchant1;
//
////        merchantRepository.findById(merchant.get().getMerchantId());
//        List<ProductInventory> productInventoryList = merchant.get().getProductInventoryList();
//        productInventoryList.add(productInventory);
//        merchantRepository
//        System.out.println(pi);
//        for(ProductInventory p:pi){
//            npi.add(p);
//        }
//        npi.add(productInventory);
//        System.out.println(npi);
//
//        merchant.get().setProductInventoryList(npi);
//        System.out.println(merchant);
//        //merchantService.updateMerchant(merchantId,merchant);
////        return  feignInterface.save(productDTO);
//        return pId;
        //return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.ACCEPTED);
        return feignInterface.save(productDTO);
    }


    @PostMapping("/addMerchants")
    public ResponseEntity<String> addMerchantDetails(@RequestParam String email,@RequestParam String  password,@RequestParam String name){
        //System.out.println(merchantDTO);
        Merchant merchant=  new Merchant();
        //BeanUtils.copyProperties(merchantDTO,merchant);
        Merchant merchantCreated = merchantService.addMerchantDetails(email,password,name);
        return new ResponseEntity<String>(merchantCreated.getMerchantId(),HttpStatus.ACCEPTED);
    }

    @PostMapping("/addProductInMerchant")
    public  String addProductInMerchant(ProductInventory productInventory)
    {
        Merchant merchant;
        return "sucess";
    }

    @GetMapping("/getPriceStockForProduct/{productId}")
    public ResponseEntity<List<MerchantProductPriceDTO>> getPriceStockForProduct(@PathVariable("productId") String productId){
        List<MerchantProductPriceDTO> merchantProductPriceDTOS = new ArrayList<>();
        return new ResponseEntity<List<MerchantProductPriceDTO>>(merchantService.showPriceMerchantStock(productId),HttpStatus.OK);
    }




    @GetMapping(value = "/getAllMerchants")
    public ResponseEntity<List<Merchant>> getAllMerchants(){
        List<Merchant> merchantsList = merchantService.findAll();
        List<MerchantDTO> merchantDTOS = new ArrayList<>();
        for(Merchant merchants : merchantsList){
            MerchantDTO merchantDTO = new MerchantDTO();
            BeanUtils.copyProperties(merchants,merchantDTO);
            merchantDTOS.add(merchantDTO);
        }
        return new ResponseEntity<>(merchantsList,HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/deleteoneMerchant/{merchantid}")
    public ResponseEntity<String> deleteMerchantId(@PathVariable("merchantid") String merchantId){
        merchantService.deleteId(merchantId);
        return new ResponseEntity<String>(merchantId,HttpStatus.ACCEPTED);
    }
    @GetMapping("/findoneMerchant/{merchantId}")
    public Merchant findOneMerchant(@PathVariable("merchantId") String merchantId){
        Optional<Merchant> merchant = merchantService.findOne(merchantId);
        Merchant merch=new Merchant();
        MerchantDTO merchantDTO = new MerchantDTO();
        if(merchant.isPresent()){

            BeanUtils.copyProperties(merchant.get(),merchantDTO);
            BeanUtils.copyProperties(merchantDTO,merch);
            return merch;
        }
        return merch;
    }


    @PutMapping("/updateMerchant/{merchantId}")
    public ResponseEntity<String> updateMerchant(@PathVariable String merchantId,@RequestBody Merchant merchant) {
        merchantService.updateMerchant(merchantId, merchant);
        return new ResponseEntity<String>("Updated succesfully", HttpStatus.OK);
    }

    @GetMapping("/getStock")
    public Integer show(@RequestParam String merchantId,@RequestParam String productId)
    {
       return merchantService.getProductStock(merchantId,productId);

    }
    @PostMapping("/editStock")
    public Integer edit(@RequestParam String merchantId,@RequestParam String productId,@RequestParam Integer qty)
    {
        return merchantService.editProductStock(merchantId,qty,productId);

    }


    @GetMapping("/validate")
    public Boolean validate(@RequestParam String merchantId,@RequestParam String productId,@RequestParam Integer qty)
    {
        return merchantService.validation(merchantId,productId,qty);

    }






}

