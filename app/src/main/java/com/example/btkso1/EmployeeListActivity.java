package com.example.btkso1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeListActivity extends AppCompatActivity {
    private ListView lvEmployees;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        lvEmployees = findViewById(R.id.lvEmployees);
        dbHelper = new DatabaseHelper(this);

        loadEmployees();

        lvEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_NAME));
                String positionStr = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_POSITION));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_EMAIL));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_PHONE));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_ADDRESS));

                Log.d("EmployeeListActivity", "name: " + name + ", position: " + positionStr + ", email: " + email + ", phone: " + phone + ", address: " + address);

                Intent intent = new Intent(EmployeeListActivity.this, EmployeeDetailActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("position", positionStr);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("address", address);

                startActivity(intent);
            }
        });

    }

    private void loadEmployees() {
        Cursor cursor = dbHelper.getAllEmployeesCursor();

        String[] from = {DatabaseHelper.COLUMN_EMPLOYEE_NAME, DatabaseHelper.COLUMN_EMPLOYEE_EMAIL};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to, 0);
        lvEmployees.setAdapter(adapter);
    }
}
