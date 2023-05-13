package com.example.ecommerce_app.network;

import com.example.ecommerce_app.model.Cart;
import com.example.ecommerce_app.model.CheckOutDTO;
import com.example.ecommerce_app.model.Merchant;
import com.example.ecommerce_app.model.MerchantSelect;
import com.example.ecommerce_app.model.OrderItems;
import com.example.ecommerce_app.model.Product;
import com.example.ecommerce_app.model.User;
import com.example.ecommerce_app.model.UserLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @GET("Products/getAllProducts")
    Call<List<Product>> getAllProducts();

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @GET("Products/searchProducts/{searchTerm}")
    Call<List<Product>> getProductsBySearch(@Path("searchTerm") String searchTerm);

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @GET("Products/findoneProduct/{productId}")
    Call<Product> getProductById(@Path("productId") String productId);
    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @GET("Merchant/getPriceStockForProduct/{productId}")
    Call<List<MerchantSelect>> getMerchantPriceByProductId(@Path("productId") String productId);

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @GET("Merchant/findoneMerchant/{merchantId}")
    Call<Merchant> getMerchantById(@Path("merchantId") String merchantId);

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @POST("cart/addToCart/{userId}/{merchantId}/{productId}/{productPrice}")
    Call<Boolean> addToCart(@Path("userId") String userId, @Path("merchantId") String merchantId, @Path("productId") String productId, @Path("productPrice") Double productPrice);


    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @GET("cart/showCart/{userId}")
    Call<Cart> showCartById(@Path("userId") String userId);


    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @POST("cart/decrementProductCount/{userId}/{productId}/{merchantId}")
    Call<Number> decrementProductCount(@Path("userId") String userId, @Path("productId") String productId, @Path("merchantId") String merchantId );

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @POST("cart/incrementProductCount/{userId}/{productId}/{merchantId}")
    Call<Number> incrementProductCount(@Path("userId") String userId, @Path("productId") String productId, @Path("merchantId") String merchantId );

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @POST("cart/removeProductFromCart/{userId}/{productId}/{merchantId}")
    Call<Boolean> removeProductFromCart(@Path("userId") String userId, @Path("productId") String productId, @Path("merchantId") String merchantId );

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @GET("User/showOrderHistory/{userId}")
    Call<List<OrderItems>> showOrderHistory(@Path("userId") String userId);


    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @POST("cart/checkout")
    Call<String> checkOut(@Body CheckOutDTO checkOutDTO);

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @POST("User/addUsers")
    Call<Boolean> registerUser(@Body User user);

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @POST("User/login")
    Call<Boolean> loginUser(@Body UserLogin userLogin);

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @GET("Merchant/getStockByMerchantProduct/{merchantId}/{productId}")
    Call<Integer> getStockByMerchantProduct(@Path("merchantId") String merchantId, @Path("productId") String productId);

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @GET("User/findoneUser/{userId}")
    Call<User> getUserById(@Path("userId") String userId);

    @Headers({"Accept: application/json", "Access-Control-Allow-Origin: 'true'"})
    @GET("cart/totalCost")
    Call<Double> getTotalCost(@Query("userId") String userId);












}
