package com.example.nischal.homkrunmenu;

/*
 * Created by nischal on 4/2/18.
 */

import org.json.JSONException;
import org.json.JSONObject;

public class MenuItem {
    private String title;
    private int price;
    public MenuItem(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public MenuItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public JSONObject getJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("n",this.title);
        jsonObject.put("p", this.price);
        return jsonObject;
    }
}
