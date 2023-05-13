package com.example.ecommerce_app.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Merchant implements Serializable {

	@SerializedName("merchantPassword")
	private String merchantPassword;

	@SerializedName("merchantEmailId")
	private String merchantEmailId;

	@SerializedName("merchantId")
	private String merchantId;

	@SerializedName("productInventoryList")
	private List<ProductInventory> productInventoryList;

	@SerializedName("merchantName")
	private String merchantName;

	public void setMerchantPassword(String merchantPassword){
		this.merchantPassword = merchantPassword;
	}

	public String getMerchantPassword(){
		return merchantPassword;
	}

	public void setMerchantEmailId(String merchantEmailId){
		this.merchantEmailId = merchantEmailId;
	}

	public String getMerchantEmailId(){
		return merchantEmailId;
	}

	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}

	public String getMerchantId(){
		return merchantId;
	}

	public void setProductInventoryList(List<ProductInventory> productInventoryList){
		this.productInventoryList = productInventoryList;
	}

	public List<ProductInventory> getProductInventoryList(){
		return productInventoryList;
	}

	public void setMerchantName(String merchantName){
		this.merchantName = merchantName;
	}

	public String getMerchantName(){
		return merchantName;
	}

	@Override
 	public String toString(){
		return 
			"Merchant{" + 
			"merchantPassword = '" + merchantPassword + '\'' + 
			",merchantEmailId = '" + merchantEmailId + '\'' + 
			",merchantId = '" + merchantId + '\'' + 
			",productInventoryList = '" + productInventoryList + '\'' + 
			",merchantName = '" + merchantName + '\'' + 
			"}";
		}
}