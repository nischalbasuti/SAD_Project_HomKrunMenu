package com.example.nischal.homkrunmenu;

/*
 * Created by nischal on 4/2/18.
 */

public class MenuItem {
    private String title;
    private String description;

    public MenuItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public MenuItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
