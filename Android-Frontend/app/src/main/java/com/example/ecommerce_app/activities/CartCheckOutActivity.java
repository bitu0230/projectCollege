package com.example.ecommerce_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce_app.R;
import com.example.ecommerce_app.application.MyApplication;
import com.example.ecommerce_app.model.CheckOutDTO;
import com.example.ecommerce_app.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartCheckOutActivity extends AppCompatActivity {

    ApiInterface apiInterfaceCart;
    CheckOutDTO checkOutDTO;

    EditText etAddressCheckOut;
    TextView tvConfirmCheckOut;
    ProgressBar pbCheckOut;
    String address;
    TextView tvTotalCheckOut;
    Double totalCost=0.0;
    ApiInterface ApiInterfaceCart;

    ImageButton ibBackCheckOut, ibCancelCheckOut;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_checkout);

        etAddressCheckOut = findViewById(R.id.et_address_check_out);
        tvTotalCheckOut = findViewById(R.id.tv_total_check_out);
        tvConfirmCheckOut = findViewById(R.id.tv_confirm_check_out);
        pbCheckOut = findViewById(R.id.pb_check_out);
        pbCheckOut.setVisibility(View.GONE);

        apiInterfaceCart=((MyApplication)getApplication()).retrofitCart.create(ApiInterface.class);
        apiInterfaceCart.getTotalCost(MyApplication.userEmail).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                totalCost = response.body();
                Log.i("Response", ""+response.body());
                setTotalCost(totalCost);
            }


            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.e("Response failed", ""+t);
            }
        });



        tvConfirmCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = String.valueOf(etAddressCheckOut.getText());
                if((address.charAt(0)==' ' || address.charAt(address.length()-1)==' '))
                {
                    Toast.makeText(CartCheckOutActivity.this, "Provide correct details", Toast.LENGTH_SHORT).show();
                } else if (address.length()<=15) {
                    Toast.makeText(CartCheckOutActivity.this, "Provide more details", Toast.LENGTH_SHORT).show();

                } else {
                    pbCheckOut.setVisibility(View.VISIBLE);
                    apiInterfaceCart=((MyApplication)getApplication()).retrofitCart.create(ApiInterface.class);
                    checkOutDTO = new CheckOutDTO();
                    checkOutDTO.setAddress(address);
                    checkOutDTO.setUserId(MyApplication.userEmail);
                    apiInterfaceCart.checkOut(checkOutDTO).enqueue(new Callback<String>() {

                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful() && response.body() != null) {

                                pbCheckOut.setVisibility(View.GONE);

                                Toast.makeText(CartCheckOutActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(CartCheckOutActivity.this, " checking out done refresh the page", Toast.LENGTH_SHORT).show();

                                Log.i("done", " " + response.body());
                            } else {
                                Toast.makeText(CartCheckOutActivity.this, " stock not available", Toast.LENGTH_SHORT).show();
                                pbCheckOut.setVisibility(View.GONE);
                                Log.i("Response failed", "" + response.code());
                            }


                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(CartCheckOutActivity.this, " something went wrong", Toast.LENGTH_SHORT).show();
                            pbCheckOut.setVisibility(View.GONE);
//                        CartProductListingActivity.super.onRestart();
                            startActivity(new Intent(CartCheckOutActivity.this, OrderProductListingActivity.class));
                            finish();

                        }
                    });

                }


            }
        });

        ibBackCheckOut = findViewById(R.id.ib_back_check_out);
        ibBackCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ibCancelCheckOut = findViewById(R.id.ib_cancel_check_out);
        ibCancelCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }
    private void setTotalCost(Double totalCost) {
        tvTotalCheckOut.setText(String.valueOf(totalCost));
    }

}
