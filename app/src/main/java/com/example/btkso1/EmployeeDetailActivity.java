package com.example.btkso1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDetailActivity extends AppCompatActivity {
    private TextView txtEmployeeDetailID, txtEmployeeDetailName, txtEmployeeDetailPosition,
            txtEmployeeDetailEmail, txtEmployeeDetailPhone, txtEmployeeDetailAddress;
    private ImageView imgEmployeeDetailAvatar;
    private Spinner spinnerDepartment;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        txtEmployeeDetailID = findViewById(R.id.txtEmployeeDetailID);
        txtEmployeeDetailName = findViewById(R.id.txtEmployeeDetailName);
        txtEmployeeDetailPosition = findViewById(R.id.txtEmployeeDetailPosition);
        txtEmployeeDetailEmail = findViewById(R.id.txtEmployeeDetailEmail);
        txtEmployeeDetailPhone = findViewById(R.id.txtEmployeeDetailPhone);
        txtEmployeeDetailAddress = findViewById(R.id.txtEmployeeDetailAddress);
        imgEmployeeDetailAvatar = findViewById(R.id.imgEmployeeDetailAvatar);
        spinnerDepartment = findViewById(R.id.spinnerDepartment);

        dbHelper = new DatabaseHelper(this);

        String employeeId = getIntent().getStringExtra("EMPLOYEE_ID");
        if (employeeId != null) {
            loadEmployeeDetails(employeeId);
        } else {
            Toast.makeText(this, "Employee ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint({"Range", "SetTextI18n"})
    private void loadEmployeeDetails(String employeeId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EMPLOYEE, null,
                DatabaseHelper.COLUMN_EMPLOYEE_ID + "=?", new String[]{employeeId},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            txtEmployeeDetailID.setText("ID: " + cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_ID)));
            txtEmployeeDetailName.setText("Tên: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_NAME)));
            txtEmployeeDetailPosition.setText("Chức vụ: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_POSITION)));
            txtEmployeeDetailEmail.setText("Email: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_EMAIL)));
            txtEmployeeDetailPhone.setText("Số điện thoại: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_PHONE)));
            txtEmployeeDetailAddress.setText("Địa chỉ: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_ADDRESS)));

            // Load departments into spinner
            loadDepartmentSpinner(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_DEPARTMENT_ID)));
        } else {
            Toast.makeText(this, "Employee not found", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    @SuppressLint("Range")
    private void loadDepartmentSpinner(int departmentId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_DEPARTMENT, null, null, null, null, null, null);
        List<String> departments = new ArrayList<>();
        int departmentPosition = 0;
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String departmentName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DEPARTMENT_NAME));
            departments.add(departmentName);
            if (cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_DEPARTMENT_ID)) == departmentId) {
                departmentPosition = departments.size() - 1;
            }
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapter);
        spinnerDepartment.setSelection(departmentPosition);
    }
}
