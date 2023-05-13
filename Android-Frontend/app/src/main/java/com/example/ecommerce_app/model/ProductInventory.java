package com.example.ecommerce_app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductInventory implements Serializable {

	@SerializedName("productId")
	private String productId;

	@SerializedName("price")
	private Integer price;

	@SerializedName("stock")
	private Integer stock;

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setPrice(Integer price){
		this.price = price;
	}

	public Integer getPrice(){
		return price;
	}

	public void setStock(Integer stock){
		this.stock = stock;
	}

	public Integer getStock(){
		return stock;
	}

	@Override
 	public String toString(){
		return 
			"ProductInventoryListItem{" + 
			"productId = '" + productId + '\'' + 
			",price = '" + price + '\'' + 
			",stock = '" + stock + '\'' + 
			"}";
		}
}