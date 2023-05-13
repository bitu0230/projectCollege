package com.example.ecommerce_app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MerchantSelect implements Serializable {

	@SerializedName("merchantId")
	private String merchantId;

	@SerializedName("productStock")
	private Integer productStock;

	@SerializedName("productPrice")
	private Double productPrice;

	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}

	public String getMerchantId(){
		return merchantId;
	}

	public void setProductStock(Integer productStock){
		this.productStock = productStock;
	}

	public Integer getProductStock(){
		return productStock;
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
			"MerchantSelect{" + 
			"merchantId = '" + merchantId + '\'' + 
			",productStock = '" + productStock + '\'' + 
			",productPrice = '" + productPrice + '\'' + 
			"}";
		}
}