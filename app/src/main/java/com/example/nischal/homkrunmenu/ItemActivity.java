package com.example.nischal.homkrunmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        String title = getIntent().getExtras().getString("title");
        this.getSupportActionBar().setTitle(title);
    }
}
