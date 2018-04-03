package com.example.nischal.homkrunmenu.persistence;

/*
 * Created by nischal on 4/3/18.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE id = :productId")
    List<Product> loadAllById(int productId);

    @Query("SELECT * FROM product WHERE id IN (:productIds)")
    List<Product> loadAllByIds(int[] productIds);

    @Query("SELECT * FROM product WHERE name LIKE :name")
    Product findByName(String name);

    @Insert
    void insert(Product product);

    @Insert
    void insertAll(Product... products);

    @Delete
    void delete(Product product);
}
