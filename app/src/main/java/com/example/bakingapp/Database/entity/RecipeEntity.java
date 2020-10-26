package com.example.bakingapp.Database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class RecipeEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
