package com.example.nischal.homkrunmenu;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nischal.homkrunmenu.persistence.AppDatabase;
import com.example.nischal.homkrunmenu.persistence.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private final String TAG = "MenuActivity";

    ArrayList<MenuItem> selectedItems = new ArrayList<>();
    AppDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //TODO: Initialize database
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database_name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        // Get menu items and store in menuItems. ##################################################
        final LinkedList<MenuItem> menuItems = getMenuItems();

        // Initialize ListView for menu items and it's adapter. ####################################
        // Attach menuItems to menuItemsAdapter.
        final MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this, menuItems);
        final ListView menuItemsListView = (ListView) findViewById(R.id.menuListView);

        menuItemsListView.setAdapter(menuItemAdapter);
        menuItemAdapter.notifyDataSetChanged();

        menuItemsListView.setOnItemClickListener(this);

        // Get the checkout floating action button and set it's onclicklistener
        FloatingActionButton fab = findViewById(R.id.checkoutFab);
        fab.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    // Handles clicks on the listview Items ########################################################
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        MenuItem currentItem = ((MenuItem)adapterView.getAdapter().getItem(position));
        CheckedTextView checkedTextView = view.findViewById(R.id.menuItemCheckbox);

        // Add/remove items to/from this.selectedItems and change UI stuff.
        if (checkedTextView.isChecked()) {
            checkedTextView.setChecked(false);
            this.selectedItems.remove(currentItem);
        } else {
            checkedTextView.setChecked(true);
            this.selectedItems.add(currentItem);
        }
    }

    // Handle clicks on this Activity ##############################################################
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkoutFab:
                if(this.selectedItems.size() == 0) {
                    // Handle case when no item is selected.
                    Toast.makeText(this, "No items selected!", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Doing stuff to generate order id.
                SharedPreferences sharedPref = getDefaultSharedPreferences(this);
                // Get the last order id.
                int lastId = 0;
                lastId = sharedPref.getInt("last_generated_id_key", lastId);

                // Generate and save the new order id.
                int currentId = lastId + 1;
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("last_generated_id_key", currentId);
                editor.commit();

                // Build a json object with order information (order id, total cost amount,
                // and products ordered)
                JSONObject OrderJsonObject = new JSONObject();

                // Build the json array with all the products to be ordered using,
                // this.selectedItems and calculate total amount of cost.
                JSONArray products = new JSONArray();
                int totalAmount = 0;
                for(MenuItem item : this.selectedItems) {
                    try {
                        products.put(item.getJsonObject());
                        totalAmount += item.getPrice();
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException");
                        e.printStackTrace();
                    }
                }
                try {
                    OrderJsonObject.put("store_id", 1); //TODO: find a way to generate unique store id.
                    OrderJsonObject.put("id", currentId); // TODO: find out how to generate unique order id.
                    OrderJsonObject.put("amount", totalAmount);
                    OrderJsonObject.put("products", products);
                } catch (JSONException e) {
                    //TODO: gracefully handel exception
                    e.printStackTrace();
                }

                // Pass the final build order json object to new activity as a string.
                Intent intent = new Intent(this, OrderActivity.class);
                intent.putExtra("OrderJsonString", OrderJsonObject.toString());
                startActivity(intent);

                break;
        }
    }

    // Get the products and return them as LinkedList<menuItems>.
    private LinkedList<MenuItem> getMenuItems() {
        final LinkedList<MenuItem> menuItems = new LinkedList<>();
//        for(int i = 0; i < 30; i++){
//            menuItems.add(new MenuItem("name"+i, i));
//        }
        for(Product product : database.productDao().getAll()) {
            menuItems.add(new MenuItem(product.getName(), product.getPrice()));
        }
        return menuItems;
    }
}
