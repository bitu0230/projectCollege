package com.example.ecommerce_app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderItems implements Serializable {
    @SerializedName("merchantId")
    private String merchantId;
    @SerializedName("productId")
    private String productId;
    @SerializedName("productQuantity")
    private Integer productQuantity;
    @SerializedName("productPrice")
    private Double productPrice;

    public void setProductQuantity(Integer productQuantity){
        this.productQuantity = productQuantity;
    }

    public Integer getProductQuantity(){
        return productQuantity;
    }

    public void setProductId(String productId){
        this.productId = productId;
    }

    public String getProductId(){
        return productId;
    }

    public void setMerchantId(String merchantId){
        this.merchantId = merchantId;
    }

    public String getMerchantId(){
        return merchantId;
    }

    public void setProductPrice(Double productPrice){
        this.productPrice = productPrice;
    }

    public Double getProductPrice(){
        return productPrice;
    }

    @Override
    public String toString(){
        return
                "CartItemsListItem{" +
                        "productQuantity = '" + productQuantity + '\'' +
                        ",productId = '" + productId + '\'' +
                        ",merchantId = '" + merchantId + '\'' +
                        ",productPrice = '" + productPrice + '\'' +
                        "}";
    }
}
