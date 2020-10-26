package com.example.bakingapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {com.example.bakingapp.Database.entity.RecipeEntity.class}, version=1)
public abstract class ApplicationDB extends RoomDatabase {
    public abstract com.example.bakingapp.Database.dao.RecipeDao dao();
    private static ApplicationDB db;

    public static ApplicationDB getInstance(Context context){
        if(db == null){
            db = Room.databaseBuilder(context, ApplicationDB.class, "TEST").allowMainThreadQueries().build();;
        }
        return db;
    }
}
