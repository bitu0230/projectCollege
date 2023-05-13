package com.example.ecommerce_app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

	@SerializedName("productRAM")
	private String productRAM;

	@SerializedName("productId")
	private String productId;

	@SerializedName("productColor")
	private String productColor;

	@SerializedName("productImg")
	private String productImg;

	@SerializedName("productBattery")
	private String productBattery;

	@SerializedName("productStorage")
	private String productStorage;

	@SerializedName("productProcessor")
	private String productProcessor;

	@SerializedName("productName")
	private String productName;

	public void setProductRAM(String productRAM){
		this.productRAM = productRAM;
	}

	public String getProductRAM(){
		return productRAM;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setProductColor(String productColor){
		this.productColor = productColor;
	}

	public String getProductColor(){
		return productColor;
	}

	public void setProductImg(String productImg){
		this.productImg = productImg;
	}

	public String getProductImg(){
		return productImg;
	}

	public void setProductBattery(String productBattery){
		this.productBattery = productBattery;
	}

	public String getProductBattery(){
		return productBattery;
	}

	public void setProductStorage(String productStorage){
		this.productStorage = productStorage;
	}

	public String getProductStorage(){
		return productStorage;
	}

	public void setProductProcessor(String productProcessor){
		this.productProcessor = productProcessor;
	}

	public String getProductProcessor(){
		return productProcessor;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	@Override
 	public String toString(){
		return 
			"Product{" + 
			"productRAM = '" + productRAM + '\'' + 
			",productId = '" + productId + '\'' + 
			",productColor = '" + productColor + '\'' + 
			",productImg = '" + productImg + '\'' + 
			",productBattery = '" + productBattery + '\'' + 
			",productStorage = '" + productStorage + '\'' + 
			",productProcessor = '" + productProcessor + '\'' + 
			",productName = '" + productName + '\'' + 
			"}";
		}
}