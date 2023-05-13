package com.example.ecommerce_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce_app.R;
import com.example.ecommerce_app.application.MyApplication;
import com.example.ecommerce_app.model.User;
import com.example.ecommerce_app.network.ApiInterface;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegisterPage extends AppCompatActivity {

    EditText etUserName;
    EditText etUserEmail;
    EditText etPassWord;
    ApiInterface apiInterfaceUser;
    MaterialButton register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_page);
        etUserName=findViewById(R.id.username);
        etUserEmail=findViewById(R.id.useremail);
        etPassWord=findViewById(R.id.password);

        register=findViewById(R.id.registerbtn);
        apiInterfaceUser=((MyApplication)getApplication()).retrofitUser.create(ApiInterface.class);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=String.valueOf(etUserName.getText());
                String userEmail=String.valueOf(etUserEmail.getText());
                String passWord=String.valueOf(etPassWord.getText());
                if(passWord.length()<8) {
                    Toast.makeText(UserRegisterPage.this,"Password must be of 8 characters ",Toast.LENGTH_SHORT).show();
                }
                else {
                    User user = new User();
                    user.setUserEmailId(userEmail);
                    user.setUserName(userName);
                    user.setUserPassword(passWord);
                    if(userName.charAt(0)==' ' || userName.charAt(userName.length()-1)==' ')
                    {
                        Toast.makeText(UserRegisterPage.this,"UserName cannot start or end with spaces ",Toast.LENGTH_SHORT).show();

                    }
                    else if(userEmail.charAt(0)==' ' || userEmail.charAt(userEmail.length()-1)==' ')
                    {
                        Toast.makeText(UserRegisterPage.this,"UserEmail cannot start or end with spaces ",Toast.LENGTH_SHORT).show();

                    }

                    else if(passWord.contains(" "))
                    {
                        Toast.makeText(UserRegisterPage.this,"password cannot contain space ",Toast.LENGTH_SHORT).show();

                    }
                    else if(userEmail.length()==0 || userName.length()==0 || passWord.length()<8)
                    {
                        Toast.makeText(UserRegisterPage.this,"Please fill all details correctly ",Toast.LENGTH_SHORT).show();

                    } else if (!isValidEmail(userEmail)) {
                        Toast.makeText(UserRegisterPage.this, "Please enter valid email ", Toast.LENGTH_SHORT).show();
                    }

                     else {
                        apiInterfaceUser.registerUser(user).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if(response.body()==false)
                                {
                                    Toast.makeText(UserRegisterPage.this, "User alredy exists ", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(UserRegisterPage.this, "Registration successful ", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(UserRegisterPage.this, LoginPage.class));
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(UserRegisterPage.this, "Something went wrong ", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            }
        });







    }
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}