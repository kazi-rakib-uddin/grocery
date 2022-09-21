package com.example.erashop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.erashop.Adapter.CartAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Fragment.CategoryFragment;
import com.example.erashop.Fragment.HomeFragment;
import com.example.erashop.Fragment.ProfileFragment;
import com.example.erashop.Fragment.WishlistFragment;
import com.example.erashop.Model.CartModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static ActivityMainBinding binding;
    ApiInterface apiInterface;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        session = new Session(MainActivity.this);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

        initView();
    }

    private void initView() {

        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout,
                new HomeFragment()).commit();

        binding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_category:
                        selectedFragment = new CategoryFragment();
                        break;
                    case R.id.nav_history:
                        selectedFragment = new WishlistFragment();
                        break;
                    case R.id.nav_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.framLayout,
                        selectedFragment).commit();


                return true;
            }
        });
        //binding.bottomNav.setSelectedItemId(R.id.nav_home);


//        binding.viewCart.setVisibility(View.GONE);



        binding.viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

        fetch_cart();

    }

    private void fetch_cart(){
        Call<String> call = apiInterface.fetch_cart(session.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals(null)){
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        int size = jsonArray.length();
                        if (size > 0){
                            binding.viewCart.setVisibility(View.VISIBLE);

                            int price=0;
                            int item=0;
                            for (int i=0;i< jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                price = price+Integer.parseInt(jsonObject.getString("price"));
                                item = item+Integer.parseInt(jsonObject.getString("quantity"));
                            }
                            MainActivity.binding.price.setText("₹"+String.valueOf(price));
                            MainActivity.binding.itemNo.setText(jsonArray.length()+" items");
                        }else{
                            binding.viewCart.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}