package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.erashop.Adapter.AddressAdapter;
import com.example.erashop.Adapter.CheckOutCartAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.CategoryModel;
import com.example.erashop.Model.MyAddressModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.ActivityCheckOutBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {
    ActivityCheckOutBinding binding;

    ArrayList<CategoryModel> products = new ArrayList<>();
    ArrayList<MyAddressModel> address = new ArrayList<>();

    CheckOutCartAdapter checkOutCartAdapter;
    AddressAdapter addressAdapter;

    ApiInterface apiInterface;
    Session session;

    String total = "";
    String type = "";
    String full_name="",phone_number="",mail_id="",pin_code ="",house_no="",area="",landmark="",state="",address_type="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        session = new Session(CheckOutActivity.this);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

        binding.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOutActivity.this, AddAddessActivity.class));
            }
        });

        binding.checkOutBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(CheckOutActivity.this, ActivitySuccess.class));
                orderPlaced();
            }
        });
//        Intent intent = new Intent();
        total = getIntent().getExtras().getString("total");

        binding.total.setText(String.format("₹%s", String.valueOf(total)));

        address();
        fetch_cart_product();

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioOnlineBTN:
                        type = "Online";
                        break;
                    case R.id.radioCashBTN:
                        type = "Cash";
                        break;
                }
            }
        });


    }

    private void address() {
        Call<String> call = apiInterface.get_address(session.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals(null)) {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")) {
                            address.add(new MyAddressModel(
                                    jsonObject.getString("full_name"),
                                    jsonObject.getString("phone_number"),
                                    jsonObject.getString("mail_id"),
                                    jsonObject.getString("pin_code"),
                                    jsonObject.getString("house_no"),
                                    jsonObject.getString("area"),
                                    jsonObject.getString("landmark"),
                                    jsonObject.getString("state"),
                                    jsonObject.getString("address_type")
                            ));

                            full_name = jsonObject.getString("full_name");
                            phone_number = jsonObject.getString("phone_number");
                            mail_id = jsonObject.getString("mail_id");
                            pin_code = jsonObject.getString("pin_code");
                            house_no = jsonObject.getString("house_no");
                            area = jsonObject.getString("area");
                            landmark = jsonObject.getString("landmark");
                            state = jsonObject.getString("state");
                            address_type = jsonObject.getString("address_type");

                            addressAdapter = new AddressAdapter(CheckOutActivity.this, address);



                            binding.rvCheckoutAddress.setAdapter(addressAdapter);

                            binding.editAddress.setVisibility(View.GONE);

                        } else {
                            binding.editAddress.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CheckOutActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_cart_product() {
        Call<String> call = apiInterface.fetch_cart(session.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals(null)) {
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                fetch_product(jsonObject.getString("product_id"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    private void fetch_product(String product_id) {
        Call<String> call = apiInterface.fetch_product_by_product_id(product_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals(null)) {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")) {
                            products.add(new CategoryModel(
                                    jsonObject.getString("product_name"),
                                    Utils.product_images + jsonObject.getString("image1")
                            ));

                            checkOutCartAdapter = new CheckOutCartAdapter(CheckOutActivity.this, products);
                            binding.rvCheckoutItem.setAdapter(checkOutCartAdapter);

                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CheckOutActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void orderPlaced() {
        String address = full_name+", "+house_no+", "+area+", "+landmark+", "+state+", "+address_type;
//        Call<String> call = apiInterface.order_placed(session.getUser_id(),total,address,pin_code,phone_number,type,)
    }

}