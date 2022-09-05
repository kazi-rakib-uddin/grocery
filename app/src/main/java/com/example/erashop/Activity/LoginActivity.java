package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    String phone, password;
    Session session;
    String user_id = "";
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        apiInterface = APIClient.getApiClient().create(ApiInterface.class);
        session = new Session(this);

        user_id = session.getUser_id();
        if(!user_id.equals("")){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }


        initView();
    }

    private void initView() {

        binding.txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        binding.btnSighIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.phone.getText().toString().equals("")) {
                    massage("Enter phone");
                } else if (binding.password.getText().toString().equals("")) {
                    massage("Enter password");
                } else {
                    login();
                }
            }
        });

        binding.txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

    }

    private void login() {
        phone = binding.phone.getText().toString();
        password = binding.password.getText().toString();

        Call<String> call = apiInterface.login(phone, password);
        ProgressUtils.showLoadingDialog(LoginActivity.this);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String res = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("0")) {
                            massage("Login Failed");
                            ProgressUtils.cancelLoading();
                        } else {
                            session.setUser_id(jsonObject.getString("id"));
                            massage("Login Successful");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            ProgressUtils.cancelLoading();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        massage(""+e.getLocalizedMessage());
                        ProgressUtils.cancelLoading();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                massage("Network Error");
                ProgressUtils.cancelLoading();
            }
        });
    }

    private void massage(String msg){
        Snackbar snackbar = Snackbar
                .make(binding.rel,msg, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

}