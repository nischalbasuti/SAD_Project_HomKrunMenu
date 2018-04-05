package com.example.nischal.homkrunmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        String orderJsonString = getIntent().getExtras().getString("OrderJsonString");
        this.getSupportActionBar().setTitle("Order Summary");
        ((TextView)findViewById(R.id.orderSummaryTv)).setText(orderJsonString);
//        Toast.makeText(this, orderJsonString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

}
