package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.databinding.ActivityAddAddessBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddessActivity extends AppCompatActivity {

    String full_name,phone_number,mail_id,pin_code,house_no,area,landmark,state="",type="";

    ApiInterface apiInterface;
    Session session;


    ActivityAddAddessBinding binding;
    private static final String[] states = new String[] {
            "West Bangel", "Mumbai", "Delhi"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        session = new Session(AddAddessActivity.this);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, states);
        binding.spinnerState.setAdapter(adapter);

        binding.spinnerState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                state = (String) adapterView.getItemAtPosition(i);

            }
        });

        binding.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.fullName.setText("");
                binding.phoneNumber.setText("");
                binding.mailId.setText("");
                binding.pinCode.setText("");
                binding.houseNo.setText("");
                binding.area.setText("");
                binding.landmark.setText("");
                binding.radioGroup.clearCheck();
            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioHomeBTN:
                        type = binding.radioHomeBTN.getText().toString();
                        break;
                    case R.id.radioOfficeBTN:
                        type = binding.radioOfficeBTN.getText().toString();
                        break;
                    case R.id.radioOthersBTN:
                        type = binding.radioOthersBTN.getText().toString();
                        break;
                }
            }
        });

        binding.addAddressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                blankChecking();

            }
        });

    }

    public void blankChecking() {

        full_name = binding.fullName.getText().toString();
        phone_number = binding.phoneNumber.getText().toString();
        mail_id = binding.mailId.getText().toString();
        pin_code = binding.pinCode.getText().toString();
        house_no = binding.houseNo.getText().toString();
        area = binding.area.getText().toString();
        landmark = binding.landmark.getText().toString();

        if (full_name.equals("")||phone_number.equals("")||mail_id.equals("")||pin_code.equals("")||house_no.equals("")
                ||area.equals("")||landmark.equals("")||state.equals("")||type.equals("")){
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
        }else{
            AddEditAddress();
        }

    }

    private void AddEditAddress() {
        ProgressUtils.showLoadingDialog(this);
        Call<String> call = apiInterface.AddAddress(session.getUser_id(),full_name,phone_number,mail_id,pin_code,house_no,area,landmark,state,type);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")){
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals('1')){
                            Toast.makeText(AddAddessActivity.this, "Address updated", Toast.LENGTH_SHORT).show();
                        }
                        else if (jsonObject.getString("rec").equals('2')){
                            Toast.makeText(AddAddessActivity.this, "Address not updated", Toast.LENGTH_SHORT).show();
                        }
                        else if (jsonObject.getString("rec").equals('3')){
                            Toast.makeText(AddAddessActivity.this, "Address added", Toast.LENGTH_SHORT).show();
                        }
                        else if (jsonObject.getString("rec").equals('4')){
                            Toast.makeText(AddAddessActivity.this, "Address not added", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                        startActivity(new Intent(AddAddessActivity.this,MyAddressActivity.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(AddAddessActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        ProgressUtils.cancelLoading();
                    }
                }else{
                    Toast.makeText(AddAddessActivity.this, "Can't Add address now", Toast.LENGTH_SHORT).show();
                }
                ProgressUtils.cancelLoading();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddAddessActivity.this, "Failed to add address now", Toast.LENGTH_SHORT).show();
                ProgressUtils.cancelLoading();
            }
        });
    }
}