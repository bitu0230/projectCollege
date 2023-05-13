package com.example.ProductsMicroService.Controller;

import com.example.ProductsMicroService.DTO.ProductDTO;
import com.example.ProductsMicroService.DTO.ProductIdDTO;
import com.example.ProductsMicroService.Entity.Products;
import com.example.ProductsMicroService.Service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Products")
@CrossOrigin(origins = "http://10.20.5.48:8080")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/addProducts")
    public ResponseEntity<String> addProductsDetails(@RequestBody ProductDTO productDTO){
        System.out.println(productDTO);
        Products product=  new Products();
        BeanUtils.copyProperties(productDTO,product);
        Products productCreated = productService.addProductsDetails(product);
        return new ResponseEntity<String>(productCreated.getProductId(),HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/getAllProducts")
    public ResponseEntity<List<ProductIdDTO>> getAllProducts(){
        List<Products> productsList = productService.findAll();
        List<ProductIdDTO> productIdDTOList = new ArrayList<>();
        for(Products products : productsList){
            ProductIdDTO productIdDTO = new ProductIdDTO();
            BeanUtils.copyProperties(products,productIdDTO);
            productIdDTOList.add(productIdDTO);

        }
        System.out.println("in get all products");
        return new ResponseEntity<List<ProductIdDTO>>(productIdDTOList,HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/searchProducts/{searchTerm}")
    public ResponseEntity<List<ProductIdDTO>> searchProducts(@PathVariable("searchTerm") String searchTerm){
        List<Products> productsList = productService.findProdutsBySearch(searchTerm);
        List<ProductIdDTO> productIdDTOList = new ArrayList<>();
        for(Products products : productsList){
            ProductIdDTO productIdDTO = new ProductIdDTO();
            BeanUtils.copyProperties(products,productIdDTO);
            productIdDTOList.add(productIdDTO);

        }
        return new ResponseEntity<List<ProductIdDTO>>(productIdDTOList,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteoneProduct/{productid}")
    public ResponseEntity<String> deleteProductId(@PathVariable("productid") String productId){
        productService.deleteId(productId);
        return new ResponseEntity<String>(productId,HttpStatus.ACCEPTED);
    }
    @GetMapping("/findoneProduct/{productId}")
    public ResponseEntity<Object> findOneProduct(@PathVariable("productId") String productId){
        Optional<Products> product = productService.findOne(productId);
        if(product.isPresent()){
            ProductDTO productDTO = new ProductDTO();
            BeanUtils.copyProperties(product.get(),productDTO);
            return new ResponseEntity<Object>(productDTO,HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Not Found",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/Page")
    public ResponseEntity<List<Products>> getPage(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize)
    {
        List<Products> list = productService.getPage(pageNo, pageSize);

        return new ResponseEntity<List<Products>>(list,HttpStatus.OK);
    }

    @PostMapping("/productNameById")
    public String productName(@RequestParam("productId") String productId){
         Optional<Products> optionalProducts=productService.findOne(productId);
         Products products=optionalProducts.get();
         return products.getProductName();

    }

}
