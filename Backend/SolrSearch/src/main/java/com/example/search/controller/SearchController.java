package com.example.search.controller;

import com.example.search.Product.Product;

import com.example.search.repository.SearchRepository;

import org.apache.solr.client.solrj.SolrClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchRepository searchRepository;
    @GetMapping("/getAllProducts")
    public List<Product> productArrayList(){
        ArrayList<Product> productArrayList = new ArrayList<>();
        Iterable<Product> productIterable = searchRepository.findAll();
        for (Product product: productIterable){
            productArrayList.add(product);
        }
        return productArrayList;

    }
    @GetMapping("/getItemsByName/{name}")
    public List<Product> getItemsByname(@PathVariable("name") String name){
        String searchvalue = name+"~";
        return searchRepository.findByProductName(searchvalue);
    }







}
