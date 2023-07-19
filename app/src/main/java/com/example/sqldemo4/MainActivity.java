package com.example.sqldemo4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_viewAll, btn_add;
    EditText et_name, et_age;
    Switch sw_activeCustomer;
    ListView lv_customerList;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       btn_viewAll = findViewById(R.id.btn_viewAll);
       btn_add = findViewById(R.id.btn_add);
       et_name = findViewById(R.id.et_name);
       et_age = findViewById(R.id.et_age);
       sw_activeCustomer = findViewById(R.id.sw_activeCustomer);
       lv_customerList = findViewById(R.id.lv_customerList);
       databaseHelper = new DatabaseHelper(MainActivity.this);
       updateListView(databaseHelper);

       btn_add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               CustomerModel customerModel;
               try {
                   customerModel = new CustomerModel(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), sw_activeCustomer.isChecked());
                   //Toast.makeText(MainActivity.this, customerModel.toString(),Toast.LENGTH_SHORT).show();
               }
               catch(Exception e){
                   customerModel = new CustomerModel(-1, "error", 0, false);
                   //Toast.makeText(MainActivity.this, "error creating customer",Toast.LENGTH_SHORT).show();
               }
           DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
           boolean added = databaseHelper.addACustomer(customerModel);
               if(added) {
                   Toast.makeText(MainActivity.this, customerModel.getName() + " added", Toast.LENGTH_SHORT).show();
               }
               updateListView(databaseHelper);
           }
       });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                updateListView(databaseHelper);
                //Toast.makeText(MainActivity.this, customerModels.toString(),Toast.LENGTH_LONG).show();
            }
        });

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomerModel clickedCustomer = (CustomerModel) adapterView.getItemAtPosition(i);
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                databaseHelper.deleteACustomer(clickedCustomer);
                updateListView(databaseHelper);
                Toast.makeText(MainActivity.this, "deleted " + clickedCustomer.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateListView(DatabaseHelper databaseHelper){
        ArrayAdapter<CustomerModel> customerModelArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getAllCustomers());
        lv_customerList.setAdapter(customerModelArrayAdapter);
    }
}