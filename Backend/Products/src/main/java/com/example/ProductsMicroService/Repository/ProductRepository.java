package com.example.ProductsMicroService.Repository;


import com.example.ProductsMicroService.Entity.Products;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Products,String>{
}
