package com.example.ecommerce_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce_app.R;
import com.example.ecommerce_app.application.MyApplication;
import com.example.ecommerce_app.model.User;
import com.example.ecommerce_app.network.ApiInterface;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;

    TextView tvUserName,tvUserEmail;

//    ApiInterface apiInterfaceUser;
    String name=null,email=null;
    ImageButton ibBackUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        tvUserEmail=findViewById(R.id.tv_userEmail_response_user_profile_page);
        tvUserName=findViewById(R.id.tv_userName_respose_user_profile_page);


        SharedPreferences sharedPreferences=getSharedPreferences("cache",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        ApiInterface apiInterfaceUser=((MyApplication)getApplication()).retrofitUser.create(ApiInterface.class);

        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null)
        {
            name=account.getDisplayName();
            email=account.getEmail();

//            tvUserName.setText(name);
//            tvUserEmail.setText(email);
            tvUserEmail.setText(email);
            tvUserName.setText(name);

            //Toast.makeText(this,"Signed in!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            email=MyApplication.userEmail;
            tvUserEmail.setText(MyApplication.userEmail);
            apiInterfaceUser.getUserById(email).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user=response.body();
                    name=user.getUserName();
                    tvUserName.setText(name);

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(UserProfileActivity.this,"noooo",Toast.LENGTH_SHORT).show();
                }
            });

        }





        ImageButton ibSignOut=findViewById(R.id.ib_user_profile_sign_out);
        ibSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("email",null);
                //deleteSharedPreferences("email");
                editor.commit();
                signOut();
            }
        });

        ibBackUserProfile = findViewById(R.id.ib_back_user_profile);
        ibBackUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    void signOut()
    {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        Toast.makeText(UserProfileActivity.this,"Signing out",Toast.LENGTH_SHORT).show();
        signout();
        MyApplication.setUserEmail(null);
        startActivity(new Intent(UserProfileActivity.this,HomeActivity.class));

    }

    void signout()
    {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
                finish();
                Toast.makeText(UserProfileActivity.this,"Signing out ",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserProfileActivity.this,HomeActivity.class));
            }
        });
    }

}