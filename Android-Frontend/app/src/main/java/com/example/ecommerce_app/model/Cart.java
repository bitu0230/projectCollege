package com.example.ecommerce_app.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Cart implements Serializable {

	@SerializedName("cartId")
	private String cartId;

	@SerializedName("cartItemsList")
	private List<CartItems> cartItemsList;

	public void setCartId(String cartId){
		this.cartId = cartId;
	}

	public String getCartId(){
		return cartId;
	}

	public void setCartItemList(List<CartItems> cartItemsList){
		this.cartItemsList = cartItemsList;
	}

	public List<CartItems> getCartItemList(){
		return cartItemsList;
	}

	@Override
 	public String toString(){
		return 
			"Cart{" + 
			"cartId = '" + cartId + '\'' + 
			",cartItemList = '" + cartItemsList + '\'' +
			"}";
		}
}