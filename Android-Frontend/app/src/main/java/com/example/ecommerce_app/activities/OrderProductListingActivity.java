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
import android.widget.Toast;

import com.example.ecommerce_app.R;
import com.example.ecommerce_app.adapter.CartProductDetailAdapter;
import com.example.ecommerce_app.adapter.OrderProductDetailAdapter;
import com.example.ecommerce_app.application.MyApplication;
import com.example.ecommerce_app.model.Cart;
import com.example.ecommerce_app.model.CartItems;
import com.example.ecommerce_app.model.OrderItems;
import com.example.ecommerce_app.model.Product;
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

public class OrderProductListingActivity extends AppCompatActivity implements OrderProductDetailAdapter.OrderProductDetailAdapterInterface {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount account;

    RecyclerView recyclerView;

    OrderProductDetailAdapter orderProductDetailAdapter;
    String userName,userEmail;
    OrderProductDetailAdapter.OrderProductDetailAdapterInterface orderProductDetailAdapterInterface=this;
    ApiInterface apiInterfaceUser;
    ApiInterface apiInterfaceProduct;

    ProgressBar pbLoadingProductsPage;

    ImageButton ibBackOrder;

    private List<OrderItems> orderItemsList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product_listing);
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
        apiInterfaceUser=((MyApplication)getApplication()).retrofitUser.create(ApiInterface.class);
        apiInterfaceUser.showOrderHistory(userEmail).enqueue(new Callback<List<OrderItems>>() {
            @Override
            public void onResponse(Call<List<OrderItems>> call, Response<List<OrderItems>> response) {
                orderItemsList=response.body();
                Log.i("see the response",""+response.body());

                pbLoadingProductsPage = findViewById(R.id.pb_loading_products_page);
                pbLoadingProductsPage.setVisibility(View.GONE);


                recyclerView=findViewById(R.id.recycler_view_order_product_listing);
                orderProductDetailAdapter=new OrderProductDetailAdapter(orderItemsList, orderProductDetailAdapterInterface, apiInterfaceProduct);

                orderProductDetailAdapter.notifyDataSetChanged();

                recyclerView.setAdapter(orderProductDetailAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            }

            @Override
            public void onFailure(Call<List<OrderItems>> call, Throwable t) {
                Log.e("oops",""+t);
            }
        });

        ibBackOrder = findViewById(R.id.ib_back_order);
        ibBackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }

    @Override
    public void onClickItem(int position, Product product) {
        Intent intent = new Intent(recyclerView.getContext(), ProductViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("productsItem", product);
//        System.out.println("+++++++++++++++++++++++");
//        System.out.println(bundle.getSerializable("productsItem").toString());
//        System.out.println("++++++++++++++++++++++++");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}