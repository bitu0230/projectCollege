package com.example.ecommerce_app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserLogin implements Serializable {

	@SerializedName("userEmail")
	private String userEmail;
	@SerializedName("userPassword")
	private String userPassword;



	public void setUserPassword(String userPassword){
		this.userPassword = userPassword;
	}

	public String getUserPassword(){
		return userPassword;
	}

	public void setUserEmail(String userEmail){
		this.userEmail = userEmail;
	}

	public String getUserEmail(){
		return userEmail;
	}

	@Override
 	public String toString(){
		return 
			"UserLogin{" + 
			"userPassword = '" + userPassword + '\'' + 
			",userEmail = '" + userEmail + '\'' + 
			"}";
		}
}