package com.example.search.repository;

import com.example.search.Product.Product;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface SearchRepository extends SolrCrudRepository<Product, String> {
    List<Product> findByProductName(String name);


}
