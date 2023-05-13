package com.example.MerchantMicroService.Repository.impl;

import com.example.MerchantMicroService.DTO.MerchantProductPriceDTO;
import com.example.MerchantMicroService.Entity.Merchant;
import com.example.MerchantMicroService.Entity.ProductInventory;
import com.example.MerchantMicroService.Repository.MerchantCustomRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MerchantCustomRepositoryImpl implements MerchantCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean saveProductInventoryByMerchantId(String merchantId, ProductInventory productInventory) {
        Query query = new Query();

        query.addCriteria(new Criteria().where("merchantId").is(merchantId));

        Update update = new Update().push("productInventoryList", productInventory);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Merchant.class);

        return !(updateResult.getMatchedCount() == 0);
    }

//    @Override
//    public List<MerchantProductPriceDTO> fetchPriceStockByProductId(String productId) {
//        Query query = new Query();
//        query.addCriteria(new Criteria().where("ProductInventoryDTO.productId").is(productId));
//        query.fields().include("merchantId");
//        query.fields().include("productInventoryList.productId");
//
//    }

//    @Override
//    public List<Merchant> getMerchantLists(String productId) {
//        Query query = new Query();
//        query.addCriteria(new Criteria().where("productId"))
//    }


}
