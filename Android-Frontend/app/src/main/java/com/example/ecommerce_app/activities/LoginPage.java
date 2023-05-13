package com.example.ecommerce_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecommerce_app.R;
import com.example.ecommerce_app.application.MyApplication;
import com.example.ecommerce_app.model.UserLogin;
import com.example.ecommerce_app.network.ApiInterface;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView gbtn;
    ApiInterface apiInterfaceUser;

    String userEmail;
    String userPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        SharedPreferences sharedPreferences=getSharedPreferences("cache",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        gbtn = findViewById(R.id.google_btn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        gbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });

        Button btnLogIn=findViewById(R.id.loginbtn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText user=findViewById(R.id.et_userEmail);
                userEmail=String.valueOf(user.getText());
                MyApplication myApplication = new MyApplication();
                EditText etPassword=findViewById(R.id.et_password);
                userPassword=String.valueOf(etPassword.getText());
                apiInterfaceUser=((MyApplication)getApplication()).retrofitUser.create(ApiInterface.class);
                UserLogin userLogin=new UserLogin();
                userLogin.setUserEmail(userEmail);
                userLogin.setUserPassword(userPassword);

//                editor.putString("password",userPassword);
                apiInterfaceUser.loginUser(userLogin).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful() && response.body()!=null) {
                            if(response.body().toString()=="Error")
                            {
                                Toast.makeText(LoginPage.this, "Incorrect username or password ", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                myApplication.setUserEmail(userEmail);
                                editor.putString("email",userEmail);
                                editor.commit();
                                Toast.makeText(LoginPage.this, "done ", Toast.LENGTH_SHORT).show();
                                finish();
                                goToNextPage();

                            }
                        }
                        else
                        {
                            Toast.makeText(LoginPage.this, "Incorrect username or password ", Toast.LENGTH_SHORT).show();

                            Log.i("Response",""+response.code());
                        }


                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(LoginPage.this,"something went wrong",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 10000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000) {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                finish();
                        goToNextPage();
            } catch (ApiException e) {
                Toast.makeText(this,"something went wrong",Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }

        }
    }

    void goToNextPage()
    {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
}