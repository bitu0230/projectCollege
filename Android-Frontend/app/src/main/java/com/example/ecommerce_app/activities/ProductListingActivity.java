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

import com.example.ecommerce_app.R;
import com.example.ecommerce_app.adapter.ProductDetailAdapter;
import com.example.ecommerce_app.application.MyApplication;
import com.example.ecommerce_app.model.Product;
import com.example.ecommerce_app.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListingActivity extends AppCompatActivity implements ProductDetailAdapter.ProductDetailAdapterInterface {

    String searchTerm;
    private List<Product>productList = new ArrayList<>();
    RecyclerView recyclerView;
    ProductDetailAdapter productDetailAdapter;

    ProductDetailAdapter.ProductDetailAdapterInterface adapterInterface = this;

    ApiInterface apiInterfaceProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);

        Bundle bundle = getIntent().getExtras();
        TextView tvSearchTerm = findViewById(R.id.tv_search_term);
        ProgressBar pbLoading = findViewById(R.id.pb_loading_products_page);
        pbLoading.setVisibility(View.VISIBLE);

        searchTerm = bundle.getString("searchTerm");
        tvSearchTerm.setText(searchTerm);

        apiInterfaceProduct=((MyApplication)getApplication()).retrofitProduct.create(ApiInterface.class);
        apiInterfaceProduct.getProductsBySearch(searchTerm).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null)
                {

                    pbLoading.setVisibility(View.GONE);

                    Log.i("Response", "" + response.body());
                    productList.addAll(response.body());

                    recyclerView=findViewById(R.id.activity_recycler_view_product_listing);
                    productDetailAdapter=new ProductDetailAdapter(productList, adapterInterface);
                    recyclerView.setAdapter(productDetailAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

                    // for horizontal scroll
//                    RecyclerView rv2=findViewById(R.id.rv_horizontal_home);
//                    imageDetailAdapter =new ImageDetailAdapter(productResponses);
//                    rv2.setAdapter(imageDetailAdapter);
//                    rv2.setLayoutManager(new GridLayoutManager(HomeActivity.this, 1, GridLayoutManager.HORIZONTAL, false));
//                    rv2.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                }
                else
                {
                    Log.i("Response failedddd", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.i("Response failedddd", "" + t);

            }
        });
        ImageButton ibProductRecyclerListing = findViewById(R.id.ib_back_product_recycler_listing);
        ibProductRecyclerListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent(recyclerView.getContext(), ProductViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("productsItem", productList.get(position));
//        System.out.println("+++++++++++++++++++++++");
//        System.out.println(bundle.getSerializable("productsItem").toString());
//        System.out.println("++++++++++++++++++++++++");
        intent.putExtras(bundle);
        startActivity(intent);

    }
}