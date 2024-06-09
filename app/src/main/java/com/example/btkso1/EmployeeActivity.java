package com.example.btkso1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {
    private EditText etEmployeeId, etEmployeeName, etEmployeePosition, etEmployeeEmail, etEmployeePhone, etEmployeeAddress;
    private Spinner spinnerDepartment;
    private Button btnSaveEmployee;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        etEmployeeId = findViewById(R.id.etEmployeeId);
        etEmployeeName = findViewById(R.id.etEmployeeName);
        etEmployeePosition = findViewById(R.id.etEmployeePosition);
        etEmployeeEmail = findViewById(R.id.etEmployeeEmail);
        etEmployeePhone = findViewById(R.id.etEmployeePhone);
        etEmployeeAddress = findViewById(R.id.etEmployeeAddress);
        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        btnSaveEmployee = findViewById(R.id.btnSaveEmployee);

        dbHelper = new DatabaseHelper(this);
        loadDepartmentsIntoSpinner();

        btnSaveEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEmployee();
            }
        });
    }

    private void loadDepartmentsIntoSpinner() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_DEPARTMENT, null, null, null, null, null, null);
        List<String> departments = new ArrayList<>();
        while (cursor.moveToNext()) {
            int departmentNameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DEPARTMENT_NAME);
            if (departmentNameIndex != -1) {
                String departmentName = cursor.getString(departmentNameIndex);
                departments.add(departmentName);
            } else {
                Log.e("EmployeeActivity", "Department name column not found");
            }
        }
        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapter);
    }


    private void saveEmployee() {
        String id = etEmployeeId.getText().toString().trim();
        String name = etEmployeeName.getText().toString().trim();
        String position = etEmployeePosition.getText().toString().trim();
        String email = etEmployeeEmail.getText().toString().trim();
        String phone = etEmployeePhone.getText().toString().trim();
        String address = etEmployeeAddress.getText().toString().trim();
        String department = spinnerDepartment.getSelectedItem().toString();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_ID, id);
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_NAME, name);
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_POSITION, position);
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_PHONE, phone);
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_ADDRESS, address);

        // Lấy ID của phòng ban từ tên phòng ban
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_DEPARTMENT, new String[]{DatabaseHelper.COLUMN_DEPARTMENT_ID},
                DatabaseHelper.COLUMN_DEPARTMENT_NAME + "=?", new String[]{department}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int departmentIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DEPARTMENT_ID);
            if (departmentIdIndex != -1) {
                int departmentId = cursor.getInt(departmentIdIndex);
                values.put(DatabaseHelper.COLUMN_EMPLOYEE_DEPARTMENT_ID, departmentId);
            } else {
                Log.e("EmployeeActivity", "Department ID column not found");
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Phòng ban không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        db = dbHelper.getWritableDatabase();
        long result = db.insert(DatabaseHelper.TABLE_EMPLOYEE, null, values);
        if (result != -1) {
            Toast.makeText(this, "Lưu thông tin nhân viên thành công!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lưu thông tin nhân viên thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}