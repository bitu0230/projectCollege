package com.example.MerchantMicroService.Repository;

import com.example.MerchantMicroService.Entity.Merchant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantRepository extends MongoRepository<Merchant, String> {

    Merchant findByMerchantId(String id);

}
