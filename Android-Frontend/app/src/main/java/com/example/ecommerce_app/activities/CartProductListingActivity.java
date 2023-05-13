package com.example.ecommerce_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce_app.R;
import com.example.ecommerce_app.adapter.CartProductDetailAdapter;
import com.example.ecommerce_app.adapter.ProductDetailAdapter;
import com.example.ecommerce_app.application.MyApplication;
import com.example.ecommerce_app.model.Cart;
import com.example.ecommerce_app.model.CartItems;
import com.example.ecommerce_app.network.ApiInterface;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartProductListingActivity extends AppCompatActivity implements CartProductDetailAdapter.CartProductDetailAdapterInterface {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount account;

    RecyclerView recyclerView;

    CartProductDetailAdapter cartProductDetailAdapter;
    String userName,userEmail;
    CartProductDetailAdapter.CartProductDetailAdapterInterface cartProductDetailAdapterInterface=this;
    ApiInterface apiInterfaceCart;
    ApiInterface apiInterfaceProduct;

    ProgressBar pbLoadingProductsPage;

    ImageButton ibBackCart;

    Boolean flag=false;

    private Cart cart;

    TextView tvBuyNowCartListing;
    Integer stock=0;


    ApiInterface apiInterfaceMerchant;
    private List<CartItems> cartItemsList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_product_listing);
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc = GoogleSignIn.getClient(this, gso);
//        account=GoogleSignIn.getLastSignedInAccount(this);
//        userName = account.getDisplayName();
//        userEmail = account.getEmail();
        userEmail=MyApplication.userEmail;
        pbLoadingProductsPage = findViewById(R.id.pb_loading_products_page);

        if(userEmail==null)
        {
            Toast.makeText(this,"login or register first",Toast.LENGTH_SHORT).show();
            pbLoadingProductsPage.setVisibility(View.GONE);
        }

        apiInterfaceProduct=((MyApplication)getApplication()).retrofitProduct.create(ApiInterface.class);
        apiInterfaceCart=((MyApplication)getApplication()).retrofitCart.create(ApiInterface.class);
        apiInterfaceCart.showCartById(userEmail).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                cart= response.body();
                cartItemsList=cart.getCartItemList();
                Log.i("see the response",""+response.body());

                pbLoadingProductsPage.setVisibility(View.GONE);
                if(cartItemsList.size()==0)
                    flag=true;
                recyclerView=findViewById(R.id.recycler_view_product_listing);
                cartProductDetailAdapter=new CartProductDetailAdapter(cartItemsList, cartProductDetailAdapterInterface, apiInterfaceProduct);

                cartProductDetailAdapter.notifyDataSetChanged();

                recyclerView.setAdapter(cartProductDetailAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Log.e("oops",""+t);
            }
        });

        ibBackCart = findViewById(R.id.ib_back_cart);
        ibBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvBuyNowCartListing = findViewById(R.id.tv_buy_now_cart_listing);
        tvBuyNowCartListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==true ||MyApplication.userEmail==null)
                {
                    Toast.makeText(CartProductListingActivity.this,"No items in cart",Toast.LENGTH_SHORT).show();

                }
                else {

                    startActivity(new Intent(CartProductListingActivity.this, CartCheckOutActivity.class));
                    finish();
                }
            }
        });

    }

    @Override
    public void onMinusClickItem(int position) {

        apiInterfaceCart.decrementProductCount(userEmail,cartItemsList.get(position).getProductId(),cartItemsList.get(position).getMerchantId()).enqueue(new Callback<Number>() {
            @Override
            public void onResponse(Call<Number> call, Response<Number> response) {
                Log.i("done",""+response.body());
                //cartProductDetailAdapter.notifyItemRangeChanged(0, cartItemsList.size());
                cartItemsList.get(position).setProductQuantity(cartItemsList.get(position).getProductQuantity()-1);
                cartProductDetailAdapter.notifyItemChanged(position);
//                finish();
//                startActivity(new Intent(CartProductListingActivity.this,CartProductListingActivity.class));


            }

            @Override
            public void onFailure(Call<Number> call, Throwable t) {
                Log.e("nope",t+"");
            }
        });


    }

    @Override
    public void onPlusClickItem(int position) {

        if (stock < cartItemsList.get(position).getProductQuantity() && stock != 0) {
            Toast.makeText(CartProductListingActivity.this, "No more stock available", Toast.LENGTH_SHORT).show();

        }
        else {
            apiInterfaceCart.incrementProductCount(userEmail, cartItemsList.get(position).getProductId(), cartItemsList.get(position).getMerchantId()).enqueue(new Callback<Number>() {
                @Override
                public void onResponse(Call<Number> call, Response<Number> response) {
                    Log.i("done", "" + response.body());
                    if (response.isSuccessful() && response.body() != null) {


                        apiInterfaceMerchant = ((MyApplication) getApplication()).retrofitMerchant.create(ApiInterface.class);
                        apiInterfaceMerchant.getStockByMerchantProduct(cartItemsList.get(position).getMerchantId(), cartItemsList.get(position).getProductId()).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                stock = response.body();
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.e("OnFailure", "" + t);
                            }
                        });
                        if (stock <= cartItemsList.get(position).getProductQuantity() && stock != 0) {
                            Toast.makeText(CartProductListingActivity.this, "No more stock available", Toast.LENGTH_SHORT).show();
                        } else {
                            cartItemsList.get(position).setProductQuantity(cartItemsList.get(position).getProductQuantity() + 1);
                            cartProductDetailAdapter.notifyItemChanged(position);
                        }

                    }

                    // cartProductDetailAdapter.notifyItemRangeChanged(0, cartItemsList.size());
//                startActivity(new Intent(CartProductListingActivity.this,CartProductListingActivity.class));
//                finish();


                }

                @Override
                public void onFailure(Call<Number> call, Throwable t) {
                    Log.e("nope", t + "");
                }
            });
        }
    }

    @Override
    public void onRemoveClickItem(int position) {

        apiInterfaceCart.removeProductFromCart(userEmail,cartItemsList.get(position).getProductId(),cartItemsList.get(position).getMerchantId()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.i("done",""+response.body());

                if(response.body()==true) {
                    cartProductDetailAdapter.notifyItemRemoved(position);
                    cartItemsList.remove(position);

                }
                if(cartItemsList.size()==0) {
                    finish();
                    startActivity(new Intent(CartProductListingActivity.this, CartProductListingActivity.class));
                }

                //cartProductDetailAdapter.notifyDataSetChanged();
                //cartProductDetailAdapter.notifyItemRangeChanged(0, cartItemsList.size());

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("nope",t+"");
            }
        });
    }
}