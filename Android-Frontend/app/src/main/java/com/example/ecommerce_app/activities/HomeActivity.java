package com.example.ecommerce_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce_app.R;
import com.example.ecommerce_app.adapter.ProductDetailAdapter;
import com.example.ecommerce_app.application.MyApplication;
import com.example.ecommerce_app.model.Product;
import com.example.ecommerce_app.network.ApiInterface;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements ProductDetailAdapter.ProductDetailAdapterInterface{
    ProductDetailAdapter productDetailAdapter;
    RecyclerView recyclerView;
    List<Product> productList =new ArrayList<>();
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    ImageView ivSamsungIcon, ivAppleIcon, ivOppoIcon, ivMiIcon, ivNokiaIcon;
    ProductDetailAdapter.ProductDetailAdapterInterface adapterInterface = this;

    TextView signOut;
    SearchView svHomePage;

    public String name,email;

    String searchTerm;
    ImageSlider imageSlider;
    ImageButton ibHomePage,ibCartHomePage,ibUserProfileHomePage,ibOrderHistoryHomePage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sharedPreferences=getSharedPreferences("cache",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();


        if(sharedPreferences.getAll().size()>0)
            MyApplication.setUserEmail(sharedPreferences.getString("email",null));

        imageSlider=findViewById(R.id.iv_slider_home_page);
        List<SlideModel> slideModels=new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.iphone_14_hero__ceub5xriecgi_large_2x,null));
        slideModels.add(new SlideModel(R.drawable.iphone_14_pro_hero__e4ivycyx40k2_large_2x,null));
        slideModels.add(new SlideModel(R.drawable.samsungg,null));
        slideModels.add(new SlideModel(R.drawable.realme,null));
        slideModels.add(new SlideModel(R.drawable.redmi,null));

        imageSlider.setImageList(slideModels,null);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null)
        {
            name=account.getDisplayName();
            email=account.getEmail();
            MyApplication.setUserEmail(email);
            //Toast.makeText(this,"Signed in!",Toast.LENGTH_SHORT).show();
        }


        List<Product> products=new ArrayList<>();

        ApiInterface apiInterfaceProduct=((MyApplication)getApplication()).retrofitProduct.create(ApiInterface.class);
        apiInterfaceProduct.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null)
                {

                    Log.i("Response", "" + response.body());
                    productList.addAll(response.body());

                    recyclerView=findViewById(R.id.rv_vertical_home);
                    productDetailAdapter=new ProductDetailAdapter(productList, adapterInterface);
                    recyclerView.setAdapter(productDetailAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),LinearLayoutManager.HORIZONTAL,false));

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



//        RecyclerView rv=findViewById(R.id.rv_vertical_home);
//        productDetailAdapter=new ProductDetailAdapter(productResponses);
//        rv.setAdapter(productDetailAdapter);
//        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
//
//        // for horizontal scroll
//        RecyclerView rv2=findViewById(R.id.rv_horizontal_home);
//        imageDetailAdapter =new ImageDetailAdapter(productResponses);
//        rv2.setAdapter(imageDetailAdapter);
//        rv2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));





//        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                if(!rv.canScrollVertically(1))
//                {
//                    Toast.makeText(HomeActivity.this,"no more products",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            });



        ibCartHomePage=findViewById(R.id.ib_cart_home_page);

        ibOrderHistoryHomePage=findViewById(R.id.ib_order_history_home_page);

        ibUserProfileHomePage=findViewById(R.id.ib_user_profile_home_page);
        ibHomePage=findViewById(R.id.ib_home_home_page);


        ibHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,HomeActivity.class));
            }
        });

        ibCartHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCart();
            }
        });

        ibOrderHistoryHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrderItems();
            }
        });

        ibUserProfileHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserProfile();
            }
        });
        ivSamsungIcon = findViewById(R.id.iv_samsung_icon);
        ivSamsungIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProductListingActivity.class);
                Bundle bundle = new Bundle();

                searchTerm = "samsung";

                if(searchTerm.isEmpty())
                {
                    Toast.makeText(HomeActivity.this, "Enter search word", Toast.LENGTH_SHORT).show();
                }
                else {
                    bundle.putString("searchTerm", searchTerm);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });

        ivAppleIcon = findViewById(R.id.iv_apple_icon);
        ivAppleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProductListingActivity.class);
                Bundle bundle = new Bundle();

                searchTerm = "iphone";

                if(searchTerm.isEmpty())
                {
                    Toast.makeText(HomeActivity.this, "Enter search word", Toast.LENGTH_SHORT).show();
                }
                else {
                    bundle.putString("searchTerm", searchTerm);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });


        ivNokiaIcon = findViewById(R.id.iv_nokia_icon);
        ivNokiaIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProductListingActivity.class);
                Bundle bundle = new Bundle();

                searchTerm = "nokia";

                if(searchTerm.isEmpty())
                {
                    Toast.makeText(HomeActivity.this, "Enter search word", Toast.LENGTH_SHORT).show();
                }
                else {
                    bundle.putString("searchTerm", searchTerm);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });


        ivMiIcon = findViewById(R.id.iv_mi_icon);
        ivMiIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProductListingActivity.class);
                Bundle bundle = new Bundle();

                searchTerm = "mi";

                if(searchTerm.isEmpty())
                {
                    Toast.makeText(HomeActivity.this, "Enter search word", Toast.LENGTH_SHORT).show();
                }
                else {
                    bundle.putString("searchTerm", searchTerm);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });


        ivOppoIcon = findViewById(R.id.iv_oppo_icon);
        ivOppoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProductListingActivity.class);
                Bundle bundle = new Bundle();

                searchTerm = "oppo";

                if(searchTerm.isEmpty())
                {
                    Toast.makeText(HomeActivity.this, "Enter search word", Toast.LENGTH_SHORT).show();
                }
                else {
                    bundle.putString("searchTerm", searchTerm);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });

//        final ImageButton ibView = findViewById(R.id.ib_dropdown_menu_home);
//
//        final PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), ibView);
//
//        final Menu menu = dropDownMenu.getMenu();
//// add your items:
//        menu.add(0, 0, 0, "Sign out");
//        menu.add(0, 1, 0, "Order history");
//        menu.add(0, 2, 0, "Show cart");
//// OR inflate your menu from an XML:
////        dropDownMenu.getMenuInflater().inflate(R.menu.some_menu, menu);
//
//        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case 0:
//                        signOut();
//                        return true;
//                    case 1:
//                        showOrderItems();
////                        Toast.makeText(HomeActivity.this, "No Order History", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case 2:
//                        showCart();
////                        Toast.makeText(HomeActivity.this, "No Cart Items", Toast.LENGTH_SHORT).show();
//                        return true;
//                }
//
//                return false;
//            }
//        });
//
//        ibView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dropDownMenu.show();
//            }
//        });

        svHomePage = findViewById(R.id.sv_home_page);
        svHomePage.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(HomeActivity.this, ProductListingActivity.class);
                Bundle bundle = new Bundle();

                searchTerm = s;

                if(searchTerm.isEmpty())
                {
                    Toast.makeText(HomeActivity.this, "Enter search word", Toast.LENGTH_SHORT).show();
                }
                else {
                    bundle.putString("searchTerm", searchTerm);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.layout_menu,menu);
        return true;
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




    void showCart()
    {
        Intent intent = new Intent(HomeActivity.this, CartProductListingActivity.class);
        //Bundle bundle = new Bundle();
        startActivity(intent);
    }

    void showOrderItems()
    {
        Intent intent = new Intent(HomeActivity.this, OrderProductListingActivity.class);
        //Bundle bundle = new Bundle();
        startActivity(intent);
    }

    void showUserProfile()
    {
        if(MyApplication.userEmail==null)
            startActivity(new Intent(HomeActivity.this,LoginOrRegister.class));
        else
            startActivity(new Intent(HomeActivity.this,UserProfileActivity.class));
    }

    void signOut()
    {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
                finish();
                Toast.makeText(HomeActivity.this,"Signing out "+MyApplication.userEmail,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this,LoginOrRegister.class));
            }
        });
    }

}