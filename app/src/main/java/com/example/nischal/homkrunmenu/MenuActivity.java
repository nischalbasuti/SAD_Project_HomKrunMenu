package com.example.nischal.homkrunmenu;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;

public class MenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Get menu items and store in menuItems. ##################################################
        final LinkedList<MenuItem> menuItems = getMenuItems();

        // Initialize ListView for menu items and it's adapter. ####################################
        // Attach menuItems to menuItemsAdapter.
        final MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this, menuItems);
        final ListView menuItemsListView = (ListView) findViewById(R.id.menuListView);

        menuItemsListView.setAdapter(menuItemAdapter);
        menuItemAdapter.notifyDataSetChanged();

        menuItemsListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // TODO: on selecting an item, start a new activity with QR code for the item and
        // maybe other information regarding the item.
        MenuItem clickedItem = ((MenuItem)adapterView.getAdapter().getItem(position));
        Toast.makeText(this, clickedItem.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("title", clickedItem.getTitle());
        startActivity(intent);
    }

    private LinkedList<MenuItem> getMenuItems() {
        //TODO: get the menu items from database and returns as a (Linked?)List<menuItem>
        final LinkedList<MenuItem> menuItems = new LinkedList<>();
        for(int i = 0; i < 30; i++){
            menuItems.add(new MenuItem("title"+i, "description"+i));
        }
        return menuItems;
    }
}
