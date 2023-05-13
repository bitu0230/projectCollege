package com.example.ProductsMicroService.Service.Impl;

import com.example.ProductsMicroService.Entity.Products;
import com.example.ProductsMicroService.Repository.ProductCustomRepository;
import com.example.ProductsMicroService.Repository.ProductRepository;
import com.example.ProductsMicroService.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCustomRepository productCustomRepository;

    public Products addProductsDetails(Products products){

        String productId = productCustomRepository.findIdByProduct(products);
        if(productId==""){
            products.setProductId(UUID.randomUUID().toString());
            return productRepository.insert(products);
        }
        else{
            products.setProductId(productId);
//            Boolean productUpdateSuccess = productCustomRepository.saveProductByID(productId, products);
            return products;
        }
    }


    @Override
    public List<Products> findAll(){
        List<Products> productsList = productRepository.findAll();
        return productsList;
    }
    @Override
    public void deleteId(String productId) {
        productRepository.deleteById(productId);
    }
    @Override
    public Optional<Products> findOne(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<Products> getPage(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Products> pagedResult = productRepository.findAll(paging);
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Products>();
        }
    }


//    @Override
//    public List<Products> getPageBySearch(Integer pageNo, Integer pageSize) {
//        Pageable paging = PageRequest.of(pageNo, pageSize);
//        Page<Products> pagedResult = productRepository.findAll(paging);
//        if(pagedResult.hasContent()) {
//            return pagedResult.getContent();
//        } else {
//            return new ArrayList<Products>();
//        }
//    }


    @Override
    public List<Products> findProdutsBySearch(String searchTerm) {
        return productCustomRepository.getAllProductsBySearchTerm(searchTerm);
    }



}
