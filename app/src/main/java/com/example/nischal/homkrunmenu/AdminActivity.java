package com.example.nischal.homkrunmenu;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nischal.homkrunmenu.persistence.AppDatabase;
import com.example.nischal.homkrunmenu.persistence.Product;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database_name").allowMainThreadQueries().build();

        Button newProductButton = findViewById(R.id.newProductButton);
        newProductButton.setOnClickListener(this);
        findViewById(R.id.getProductButton).setOnClickListener(this);
        findViewById(R.id.deleteProductButton).setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onClick(View view) {
        EditText newProductIdEt = findViewById(R.id.newProductIdEt);
        EditText newProductNameEt = findViewById(R.id.newProductNameEt);
        EditText newProductPriceEt = findViewById(R.id.newProductPriceEt);
        Product product = new Product();

        if(newProductIdEt.getText().length() > 0){
            product.setId(Integer.parseInt(newProductIdEt.getText().toString()));
        }

        switch (view.getId()) {
            case R.id.newProductButton:
                //TODO: make sure price is an int.
                if(newProductNameEt.getText().length() <= 0 || newProductPriceEt.length() <=0) {
                    return;
                }
                product.setName(newProductNameEt.getText().toString());
                product.setPrice(Integer.parseInt(newProductPriceEt.getText().toString()));

                if (!database.productDao().loadAllById(product.getId()).isEmpty()) {
                    // if id is already used, return.
                    Toast.makeText(this,
                            "id "+product.getId()+" is not unique.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                database.productDao().insert(product);

                Toast.makeText(this, "New product added.", Toast.LENGTH_SHORT).show();
                newProductIdEt.setText("");
                newProductNameEt.setText("");
                newProductPriceEt.setText("");
                break;
            case R.id.deleteProductButton:
                if (database.productDao().loadAllById(product.getId()).isEmpty()){
                    Toast.makeText(this,
                            "id "+product.getId()+" does not exist.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                database.productDao().delete(product);
                break;
            case R.id.getProductButton:
                if (database.productDao().loadAllById(product.getId()).isEmpty()){
                    Toast.makeText(this,
                            "id "+product.getId()+" does not exist.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this,
                        database.productDao().loadAllById(product.getId()).get(0).toString(),
                        Toast.LENGTH_SHORT).show();
        }
    }
}
