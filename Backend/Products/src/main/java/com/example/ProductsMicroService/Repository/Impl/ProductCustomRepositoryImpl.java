package com.example.ProductsMicroService.Repository.Impl;

import com.example.ProductsMicroService.Entity.Products;
import com.example.ProductsMicroService.Repository.ProductCustomRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;


    public String findIdByProduct(Products products) {


        Products products1 = new Products();
        Query query = new Query();
        Criteria criteria = new Criteria();
        final List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(criteria.where("productName").is(products.getProductName()));
        criteriaList.add(criteria.where("productColor").is(products.getProductColor()));
        criteriaList.add(criteria.where("productRAM").is(products.getProductRAM()));
        criteriaList.add(criteria.where("productStorage").is(products.getProductStorage()));
        criteriaList.add(criteria.where("productBattery").is(products.getProductBattery()));

        criteria.andOperator(criteriaList);

        if (!criteriaList.isEmpty()) {
            query.addCriteria(criteria);

        }

        List<Products> productsList = mongoTemplate.find(query, Products.class);
        if(CollectionUtils.isEmpty(productsList))
            return "";
        return productsList.get(0).getProductId();

    }

    @Override
    public boolean saveProductByID(String productId, Products products) {
        Query query = new Query();
        query.addCriteria(new Criteria().where("productId").is(productId));
        Update update = new Update().push("products", products);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Products.class);

        return !(updateResult.getMatchedCount()==0);

    }

    @Override
    public List<Products> getAllProductsBySearchTerm(String searchTerm) {

        searchTerm = ".*"+searchTerm+".*";

        Criteria criteria = new Criteria().orOperator(
                Criteria.where("productName").regex(searchTerm, "i"),
                Criteria.where("productRAM").regex(searchTerm, "i"),
                Criteria.where("productStorage").regex(searchTerm, "i"),
                Criteria.where("productBattery").regex(searchTerm, "i"));

        List<Products> productsList = mongoTemplate.find(Query.query(criteria), Products.class);
        return productsList;

    }

}
