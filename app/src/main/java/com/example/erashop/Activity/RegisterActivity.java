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
import com.example.erashop.databinding.ActivityRegisterBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    String name, email, phone, password;
    ApiInterface apiInterface;
    Pattern phonePattern = Pattern.compile("^[6-9]\\d{9}$");
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

        session = new Session(this);

        binding.btnSighIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
//                finish();

                if(binding.name.getText().toString().equals("")){
                    massage("Enter name");
                }else if(binding.email.getText().toString().equals("")){
                    massage("Enter mail");
                }else if(binding.phone.getText().toString().equals("")){
                    massage("Enter phone number");
                }else if(!phonePattern.matcher(binding.phone.getText().toString()).matches()){
                    binding.phone.setError("Enter a valid phone number");
                }else if(binding.password.getText().toString().equals("")){
                    massage("Enter password");
                }else if(binding.confirmPassword.getText().toString().equals("")){
                    massage("Re-enter password");
                }else if(!binding.password.getText().toString().equals(binding.confirmPassword.getText().toString())){
                    massage("Password doesn't match");
                }else{
                    register();
                }

            }
        });


        binding.txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();

            }
        });
    }




    private void register() {
        name = binding.name.getText().toString();
        email = binding.email.getText().toString();
        phone = binding.phone.getText().toString();
        password = binding.password.getText().toString();

        Call<String> call = apiInterface.register(name, email, phone,password);
        ProgressUtils.showLoadingDialog(RegisterActivity.this);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String res = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if(jsonObject.getString("rec").equals("0")){
                            massage("Registration Failed");
                            ProgressUtils.cancelLoading();
                        }
                        else if(jsonObject.getString("rec").equals("2")){
                            massage("User Exists");
                            ProgressUtils.cancelLoading();
                        }
                        else{
                            session.setUser_id(jsonObject.getString("user_id"));
                            massage("Registration successful");
                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            ProgressUtils.cancelLoading();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        massage(""+e.getLocalizedMessage());
                        ProgressUtils.cancelLoading();
                    }
                }else{
                    massage("No Response");
                    ProgressUtils.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                massage("Slow Network");
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