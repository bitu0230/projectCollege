package com.example.elasticProduct.controllers;


import com.example.elasticProduct.documents.Products;
import com.example.elasticProduct.search.SearchRequestDTO;
import com.example.elasticProduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService service){
        this.productService = service;
    }

    @PostMapping
    public ResponseEntity<String> index(@RequestBody final Products product) {
        productService.index(product);
        return new ResponseEntity<String>("Data is entered", HttpStatus.ACCEPTED);
    }

    @GetMapping("/{productName}")
    public Products getByName(@PathVariable final String productName) {
        return productService.getById(productName);
    }

    @PostMapping("/search")
    public List<Products> search(@RequestBody final SearchRequestDTO dto) {
        return productService.search(dto);
    }

}
