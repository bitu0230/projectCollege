package com.example.ProductsMicroService.Service;

import com.example.ProductsMicroService.Entity.Products;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Products addProductsDetails(Products products);

    List<Products> findAll();

    void deleteId(String productId);

    Optional<Products> findOne(String productId);

    List<Products> getPage(Integer pageNo, Integer pageSize);

    List<Products> findProdutsBySearch(String searchTerm);
}
