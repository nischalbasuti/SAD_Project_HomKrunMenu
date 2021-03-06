package com.example.nischal.homkrunmenu;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MenuActivity";

    ArrayList<MenuItem> selectedItems = new ArrayList<>();
    private LinkedList<MenuItem> menuItems;

    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Initialize database
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database_name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        // Get menu items
        this.menuItems = this.getMenuItems();

        // Initialize ListView for menu items and it's adapter. ####################################
        // Attach menuItems to menuItemsAdapter.
        final MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this, menuItems);
        final ListView menuItemsListView = (ListView) findViewById(R.id.menuListView);

        menuItemsListView.setAdapter(menuItemAdapter);
        menuItemAdapter.notifyDataSetChanged();

        // Get the checkout floating action button and set it's onclicklistener
        FloatingActionButton fab = findViewById(R.id.checkoutFab);
        fab.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    // Handle clicks on this Activity ##############################################################
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkoutFab:
                for (MenuItem menuItem : this.menuItems) {
                    if(menuItem.getCount() > 0) {
                        this.selectedItems.add(menuItem);
                    }
                }
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
                    OrderJsonObject.put("total_price", totalAmount);
                    OrderJsonObject.put("products", products);
                    OrderJsonObject.put("url", "http://foobar.com/"); //TODO: change url
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
        for(Product product : database.productDao().getAll()) {
            menuItems.add(new MenuItem(product.getName(), product.getPrice()));
        }
        return menuItems;
    }
}
