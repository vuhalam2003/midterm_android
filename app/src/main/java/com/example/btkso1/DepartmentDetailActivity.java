package com.example.btkso1;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DepartmentDetailActivity extends AppCompatActivity {
    private TextView tvName, tvEmail, tvWebsite, tvAddress, tvPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_detail);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvWebsite = findViewById(R.id.tvWebsite);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhone = findViewById(R.id.tvPhone);

        // Get data from intent
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String website = getIntent().getStringExtra("website");
        String address = getIntent().getStringExtra("address");
        String phone = getIntent().getStringExtra("phone");

        Log.d("DepartmentDetailActivity", "name: " + name + ", email: " + email + ", website: " + website + ", address: " + address + ", phone: " + phone);

        // Set data to views
        tvName.setText(name);
        tvEmail.setText(email);
        tvWebsite.setText(website);
        tvAddress.setText(address);
        tvPhone.setText(phone);
    }

}
