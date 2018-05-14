package com.example.nischal.homkrunmenu;

/*
 * Created by nischal on 4/2/18.
 */

import org.json.JSONException;
import org.json.JSONObject;

public class MenuItem {
    private String title;
    private int price;

    // Use this to store the number of this item that are to be ordered.
    private int count;

    public MenuItem(String title, int price) {
        this.title = title;
        this.price = price;
        this.count = 0;
    }

    public MenuItem() {
        this.count = 0;
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

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void countPlusOne() {
        this.count +=1;
    }

    public void countMinusOne() {
        if (this.count >= 1) {
            this.count -= 1;
        }
    }
}
