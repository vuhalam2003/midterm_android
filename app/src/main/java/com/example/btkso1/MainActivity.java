package com.example.btkso1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnDepartmentManager, btnEmployeeManager, btnSearch, btnLogOut, btnViewDepartments, btnViewEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDepartmentManager = findViewById(R.id.btnDepartmentManager);
        btnEmployeeManager = findViewById(R.id.btnEmployeeManager);
        btnSearch = findViewById(R.id.btnSearch);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnViewDepartments = findViewById(R.id.btnViewDepartments);
        btnViewEmployees = findViewById(R.id.btnViewEmployees);

        btnDepartmentManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DepartmentActivity.class);
                startActivity(intent);
            }
        });

        btnEmployeeManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear session information here (if any)

                // Navigate back to the login screen
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close MainActivity so the user cannot go back using the Back button
            }
        });

        btnViewDepartments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DepartmentListActivity.class);
                startActivity(intent);
            }
        });

        btnViewEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmployeeListActivity.class);
                startActivity(intent);
            }
        });
    }
}
