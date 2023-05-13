package com.example.MerchantMicroService.Service.Impl;

import com.example.MerchantMicroService.DTO.MerchantDTO;
import com.example.MerchantMicroService.DTO.MerchantProductPriceDTO;
import com.example.MerchantMicroService.Entity.Merchant;
import com.example.MerchantMicroService.Entity.ProductInventory;
import com.example.MerchantMicroService.Repository.MerchantCustomRepository;
import com.example.MerchantMicroService.Repository.MerchantRepository;
import com.example.MerchantMicroService.Service.MerchantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    MerchantCustomRepository merchantCustomRepository;


    public void addProductInventoryByMerchantId(String merchantId, ProductInventory productInventory) {
        merchantCustomRepository.saveProductInventoryByMerchantId(merchantId, productInventory);

    }


    public Merchant addMerchantDetails(String merchantName, String merchantPaasword,String name){

        Merchant merchant = new Merchant();
        merchant.setMerchantName(merchantName);
        merchant.setMerchantPassword(merchantPaasword);
        merchant.setMerchantName(name);
        merchant.setProductInventoryList(Collections.emptyList());
        if(merchant.getMerchantId()==null){
            merchant.setMerchantId(UUID.randomUUID().toString());
            return merchantRepository.insert(merchant);
        }
        else{
            return  merchantRepository.save(merchant);
        }
    }
    @Override
    public List<Merchant> findAll(){
        List<Merchant> merchantsList = merchantRepository.findAll();
        return merchantsList;
    }
    @Override
    public void deleteId(String merchantId) {
        merchantRepository.deleteById(merchantId);
    }
    @Override
    public Optional<Merchant> findOne(String merchantId) {
        return merchantRepository.findById(merchantId);
    }

    @Override
    public Merchant updateMerchant(String merchantId, Merchant merchant) {
        Optional<Merchant> studentOptional = merchantRepository.findById(merchantId);
        if(((Optional) studentOptional).isPresent()) {
            Merchant studentData = studentOptional.get();
            studentData.setMerchantName(merchant.getMerchantName());
            studentData.setMerchantName(merchant.getMerchantName());
            studentData.setMerchantPassword(merchant.getMerchantPassword());
            studentData.setMerchantEmailId(merchant.getMerchantEmailId());
            return merchantRepository.save(studentData);
        }
        else {
            throw new IllegalArgumentException("student not found");
        }

    }


    public Integer findStock(String merchantId,String productId)
    {
        //Merchant merchant1;
        Optional<Merchant> optionalMerchant=merchantRepository.findById(merchantId);
        List<ProductInventory>productInventories;
        Integer stock=0;
        if(optionalMerchant.isPresent()) {
            Merchant merchant = optionalMerchant.get();
            productInventories=merchant.getProductInventoryList();

            for (ProductInventory pd:productInventories){
                if(pd.getProductId().equals(productId))
                    stock=pd.getStock();
            }
        } else {
            throw new RuntimeException("merchant not present");
        }

        return stock;
    }

    @Override
    public int editProductStock(String merchantId, Integer qty, String productId) {

        Optional<Merchant> merchant=merchantRepository.findById(merchantId);
        List<ProductInventory>productInventories;
        productInventories=merchant.get().getProductInventoryList();

        Integer stock=0;
        if(merchant.isPresent()) {
            Merchant merchant1 = merchant.get();
            productInventories=merchant1.getProductInventoryList();

            List<ProductInventory> productInventoryList=new ArrayList<>();
            for (ProductInventory pd:productInventories){
                if(pd.getProductId().equals(productId)) {
                    pd.setStock(pd.getStock()-qty);
                }
            }
            merchantRepository.save(merchant.get());
            return 1;
        } else {
            throw new RuntimeException("merchant not present");
        }

    }

    @Override
    public int getProductStock(String merchantId, String productId) {
        return findStock(merchantId,productId);
    }

    @Override
    public Boolean validation(String merchantId, String productId,Integer qty) {
        Integer s=findStock(merchantId,productId);
        if(s-qty<0)
            return false;
        else
            return true;
    }

    @Override
    public List<MerchantProductPriceDTO> showPriceMerchantStock(String pid) {
        ArrayList<MerchantProductPriceDTO> mp=new ArrayList<>();
        List<Merchant> merchantList= new ArrayList<>();
        merchantList =merchantRepository.findAll();
        for(Merchant m:merchantList){
            List<ProductInventory> productInventoryList= new ArrayList<>();
            productInventoryList=m.getProductInventoryList();
            for (ProductInventory productInventory: productInventoryList){
                System.out.println(productInventory.getProductId());
                if(productInventory.getProductId().equals(pid)){
                    MerchantProductPriceDTO merchantProductPriceDTO=new MerchantProductPriceDTO();
                    merchantProductPriceDTO.setMerchantId(m.getMerchantId());
                    merchantProductPriceDTO.setProductPrice(productInventory.getPrice());
                    merchantProductPriceDTO.setProductStock(productInventory.getStock());
                    mp.add(merchantProductPriceDTO);
                }
            }
        }
//        MerchantProductPriceDTO merchantProductPriceDTO=new MerchantProductPriceDTO();
        Collections.sort(mp, new Comparator<MerchantProductPriceDTO>() {
            @Override
            public int compare(MerchantProductPriceDTO o1, MerchantProductPriceDTO o2) {
                return o2.getProductPrice().compareTo(o1.getProductPrice());
            }
        });
        return mp;
    }

}
