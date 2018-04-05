package com.example.nischal.homkrunmenu.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/*
 * Created by nischal on 4/3/18.
 */

@Database(entities = {Product.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
