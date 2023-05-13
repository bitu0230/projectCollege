package com.example.ecommerce_app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {


	@SerializedName("userEmailId")
	private String userEmailId;

	@SerializedName("userName")
	private String userName;

	@SerializedName("userPassword")
	private String userPassword;

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword(){
		return userPassword;
	}

	public String getUserEmailId(){
		return userEmailId;
	}

	public String getUserName(){
		return userName;
	}
}