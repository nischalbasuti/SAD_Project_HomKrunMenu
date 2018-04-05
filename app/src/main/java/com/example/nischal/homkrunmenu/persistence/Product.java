package com.example.nischal.homkrunmenu.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * Created by nischal on 4/3/18.
 */

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "price")
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Ignore
    public JSONObject getJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",this.name);
        jsonObject.put("price", this.price);
        return jsonObject;
    }

    @Ignore
    public String toString() {
        try {
            return getJsonObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "couldn't get string for: "+super.toString();
    }
}
