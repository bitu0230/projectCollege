package com.example.ecommerce_app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckOutDTO implements Serializable {

	@SerializedName("address")
	private String address;

	@SerializedName("userId")
	private String userId;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	@Override
 	public String toString(){
		return 
			"CheckOutDTO{" + 
			"address = '" + address + '\'' + 
			",userId = '" + userId + '\'' + 
			"}";
		}
}