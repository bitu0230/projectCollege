package com.example.ProductsMicroService.Repository;

import com.example.ProductsMicroService.Entity.Products;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductCustomRepository {
    public String findIdByProduct(Products products) ;

    public boolean saveProductByID(String productId, Products products);

    public List<Products> getAllProductsBySearchTerm(String searchTerm);


    }
