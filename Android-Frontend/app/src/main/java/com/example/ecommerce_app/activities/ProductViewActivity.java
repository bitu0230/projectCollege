package com.example.ecommerce_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce_app.R;
import com.example.ecommerce_app.application.MyApplication;
import com.example.ecommerce_app.model.Merchant;
import com.example.ecommerce_app.model.MerchantSelect;
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

public class ProductViewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Product product = new Product();
    private  int flag=0;
    private List<MerchantSelect> merchantSelectList = new ArrayList<>();
    List<Merchant> merchantList = new ArrayList<>();
    List<String> merchantNames = new ArrayList<String>();
    Merchant merchant;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Bundle bundle1;
    ImageView ivProductImageProductView;
    TextView tvProductNameProductView;
    TextView tvProductRAMProductView;
    TextView tvProductColorProductProductView;
    TextView tvStorageProductView;
    TextView tvProcessorProductView;
    TextView tvBatteryProductView;
    ApiInterface apiInterfaceMerchant;
    ApiInterface apiInterfaceCart;
    ImageButton ibBackProductView;
    TextView tvProductPriceProductView;
    TextView tVAddToCartProductView;
    Spinner spinnerProductView;
    int i = 0;
    ArrayAdapter arrayAdapter;

    GoogleSignInAccount account;

    int selectedMerchantPosition;
    String userName;
    String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        bundle1 = getIntent().getExtras();

//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc = GoogleSignIn.getClient(this, gso);
//        account=GoogleSignIn.getLastSignedInAccount(this);
//        userName = account.getDisplayName();
//        userEmail = account.getEmail();
        userEmail=MyApplication.userEmail;


        product = (Product) (bundle1.getSerializable("productsItem"));

        ivProductImageProductView = findViewById(R.id.iv_product_view);
        String productImg = product.getProductImg();
        Glide.with(ivProductImageProductView.getContext()).load(String.valueOf(productImg)).into(ivProductImageProductView);

        tvProductNameProductView = findViewById(R.id.tv_product_name_product_view);
        String productName = product.getProductName();
        tvProductNameProductView.setText(productName);

        tvProductColorProductProductView = findViewById(R.id.tv_product_color_product_view);
        String productColor = product.getProductColor();
        tvProductColorProductProductView.setText(productColor);

        tvProductRAMProductView = findViewById(R.id.tv_product_RAM_product_view);
        String productRAM = product.getProductRAM();
        tvProductRAMProductView.setText(productRAM);

        tvStorageProductView = findViewById(R.id.tv_storage_product_view);
        String productStorage = product.getProductStorage();
        tvStorageProductView.setText(productStorage);

        tvProcessorProductView = findViewById(R.id.tv_processor_product_view);
        String productProcessor = product.getProductProcessor();
        tvProcessorProductView.setText(productProcessor);

        tvBatteryProductView = findViewById(R.id.tv_battery_product_view);
        String productBattery = product.getProductBattery();
        tvBatteryProductView.setText(productBattery);

        System.out.println("+++++++++++++++++++++\n");
        System.out.println("productId :"+product.getProductId());
        System.out.println("+++++++++++++++++++++\n");

        apiInterfaceMerchant = ((MyApplication) getApplication()).retrofitMerchant.create(ApiInterface.class);
        apiInterfaceMerchant.getMerchantPriceByProductId(product.getProductId()).enqueue(new Callback<List<MerchantSelect>>() {
            @Override
            public void onResponse(Call<List<MerchantSelect>> call, Response<List<MerchantSelect>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Log.i("Response", "" + response.body());
                    merchantSelectList.addAll(response.body());

//                    for(MerchantSelect m : merchantSelectList){
//                        System.out.println("tick==="+m);
//                        System.out.println("price"+m.getProductPrice());
//                    }
//                    System.out.println("insider"+merchantSelectList);
//                    System.out.println("aks"+response.body());

                    setSpinnerDropDownMenu();

//                    System.out.println(merchantSelectList);
                } else {
                    Log.i("Response failed", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<MerchantSelect>> call, Throwable t) {

                Log.i("Response failed", "" + t);
            }
        });


        ibBackProductView = findViewById(R.id.ib_back_product_view);
        ibBackProductView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tVAddToCartProductView = findViewById(R.id.tv_add_to_cart_product_view);
        tVAddToCartProductView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userEmail==null)
                {
                    Toast.makeText(ProductViewActivity.this,"login or regitser first",Toast.LENGTH_SHORT).show();

                }
                else {
                    apiInterfaceCart = ((MyApplication) getApplication()).retrofitCart.create(ApiInterface.class);
                    apiInterfaceCart.addToCart(userEmail, merchantSelectList.get(selectedMerchantPosition).getMerchantId(), product.getProductId(), merchantSelectList.get(selectedMerchantPosition).getProductPrice()).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Log.i(ProductViewActivity.class.getSimpleName(), response.body() + "");
                            Toast.makeText(ProductViewActivity.this, getResources().getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.i("Response failed", "" + t);
                            Toast.makeText(ProductViewActivity.this, getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }



    private void setSpinnerDropDownMenu() {

        setSpinnerAdapter();
        for(MerchantSelect merchantSelect: merchantSelectList) {
            apiInterfaceMerchant = ((MyApplication) getApplication()).retrofitMerchant.create(ApiInterface.class);
            apiInterfaceMerchant.getMerchantById(merchantSelect.getMerchantId()).enqueue(new Callback<Merchant>() {
                @Override
                public void onResponse(Call<Merchant> call, Response<Merchant> response) {
                    if (response.isSuccessful() && response.body() != null) {

                        Log.i("Response", "" + response.body());
                        merchant = response.body();
                        merchantList.add(merchant);

                        merchantNames.add(merchant.getMerchantName()+" -> Rs."+merchantSelectList.get(i).getProductPrice()+" Stock -> "+merchantSelectList.get(i).getProductStock());
                        i+=1;
                        arrayAdapter.notifyDataSetChanged();
//                    System.out.println(merchantSelectList);
                    } else {
                        Log.i("Response failed in setting D" + "Dropdown Menu", "" + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Merchant> call, Throwable t) {

                    Log.i("Response failed", "" + t);
                }
            });
        }

    }


    public void setSpinnerAdapter() {
        System.out.println(merchantNames);
        spinnerProductView = findViewById(R.id.action_bar_spinner_product_view);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, merchantNames);
        spinnerProductView.setAdapter(arrayAdapter);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductView.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//            int positionSelected = spinnerProductView.getSelectedItemPosition();
        String item_position = String.valueOf(position);
        System.out.println("item_position === "+item_position);
        int positionInt = Integer.valueOf(item_position);
        selectedMerchantPosition= positionInt;
        System.out.println("+++++"+merchantNames.get(selectedMerchantPosition));
        tvProductPriceProductView = findViewById(R.id.tv_product_price_product_view);
        tvProductPriceProductView.setText(String.valueOf(merchantSelectList.get(positionInt).getProductPrice()));

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}