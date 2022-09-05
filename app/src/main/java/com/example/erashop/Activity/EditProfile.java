package com.example.erashop.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.databinding.ActivityEditProfileBinding;
import com.example.erashop.databinding.BottomDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.example.erashop.ApiClient.APIClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    BottomSheetDialog bottomSheetDialog;
    BottomDialogBinding binding2;
    private String name, email, phone, id, profile_picture;
    ApiInterface apiInterface;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        apiInterface = APIClient.getApiClient().create(ApiInterface.class);
        session = new Session(this);

        id = session.getUser_id();

        binding2 = BottomDialogBinding.inflate(getLayoutInflater());

        binding.CameraBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(view);
            }
        });

        binding.profileBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.updateProfileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.name.getText().toString().equals("")) {
                    massage("Enter phone");
                } else if (binding.phone.getText().toString().equals("")) {
                    massage("Enter password");
                } else if (binding.email.getText().toString().equals("")) {
                    massage("Enter password");
                } else {
                    update();
                }
            }
        });

    }

    private void show(View view) {
        {

            bottomSheetDialog = new BottomSheetDialog(EditProfile.this, R.style.BottomSheetDialogTheme);

            View view1 = LayoutInflater.from(EditProfile.this).inflate(R.layout.bottom_dialog,
                    (RelativeLayout) view.findViewById(R.id.bottom_dialog));

            LinearLayout gal = view1.findViewById(R.id.gallery);
            LinearLayout cam = view1.findViewById(R.id.camera);
            ImageView imgCancel = view1.findViewById(R.id.img_cancel);

            cam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent cam_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cam_intent, 1);
                }
            });

            gal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 2);
                }
            });

            imgCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog.dismiss();
                }
            });

            bottomSheetDialog.setContentView(view1);
            bottomSheetDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    binding.circularImg.setImageBitmap(photo);
                    profile_picture = BitMapToString(photo);
                    bottomSheetDialog.dismiss();
                    upload_profile_image(profile_picture);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        binding.circularImg.setImageBitmap(bitmap);
                        profile_picture = BitMapToString(bitmap);
                        upload_profile_image(profile_picture);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    bottomSheetDialog.dismiss();
                }
                break;
        }

    }

    private void upload_profile_image(String profile_picture) {
        Call<String> call = apiInterface.upload_profile_image(profile_picture);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String resp = response.body();
                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    if (jsonObject.getString("rec").equals("0")) {
                        massage("Can't update profile picture now");
                    } else {
                        massage("profile picture updated");
                    }

                } catch (JSONException e) {
                    massage("" + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void massage(String msg) {
        Snackbar snackbar = Snackbar.make(binding.rel, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void update() {
        name = binding.name.getText().toString();
        phone = binding.phone.getText().toString();
        email = binding.email.getText().toString();

        Call<String> call = apiInterface.update(id, name, phone, email);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String resp = response.body();
                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    if (jsonObject.getString("rec").equals("0")) {
                        massage("Can't update details now.Try again later.");
                    } else {
                        massage("Personal details updated");
                    }
                } catch (JSONException e) {
                    massage("" + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}