package com.example.ecommerce_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecommerce_app.R;

public class UserOrMerchant extends AppCompatActivity {

    Button user;
    Button merchant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_or_merchant);
        user=findViewById(R.id.UserLogin);
        merchant=findViewById(R.id.Merchantlogin);
        user.setText("User");
        merchant.setText("Merchant");

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userApp();
            }
        });

        merchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merchantApp();
            }
        });
    }

    void userApp()
    {
        Intent intent=new Intent(UserOrMerchant.this,LoginPage.class);
        startActivity(intent);
    }
    void merchantApp()
    {

    }
}