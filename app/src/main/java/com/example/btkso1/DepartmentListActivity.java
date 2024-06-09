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

public class DepartmentListActivity extends AppCompatActivity {
    private ListView lvDepartments;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);

        lvDepartments = findViewById(R.id.lvDepartments);
        dbHelper = new DatabaseHelper(this);

        loadDepartments();

        lvDepartments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DEPARTMENT_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DEPARTMENT_EMAIL));
                String website = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DEPARTMENT_WEBSITE));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DEPARTMENT_ADDRESS));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DEPARTMENT_PHONE));

                Log.d("DepartmentListActivity", "name: " + name + ", email: " + email + ", website: " + website + ", address: " + address + ", phone: " + phone);

                Intent intent = new Intent(DepartmentListActivity.this, DepartmentDetailActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("website", website);
                intent.putExtra("address", address);
                intent.putExtra("phone", phone);

                startActivity(intent);
            }
        });

    }

    private void loadDepartments() {
        Cursor cursor = dbHelper.getAllDepartmentsCursor();

        String[] from = {DatabaseHelper.COLUMN_DEPARTMENT_NAME, DatabaseHelper.COLUMN_DEPARTMENT_EMAIL};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to, 0);
        lvDepartments.setAdapter(adapter);
    }
}
