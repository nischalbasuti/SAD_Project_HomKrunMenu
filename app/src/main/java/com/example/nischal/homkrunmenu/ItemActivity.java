package com.example.nischal.homkrunmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        String OrderJsonString = getIntent().getExtras().getString("OrderJsonString");
        this.getSupportActionBar().setTitle("Order Summary");
        Toast.makeText(this, OrderJsonString, Toast.LENGTH_LONG).show();
    }
}
