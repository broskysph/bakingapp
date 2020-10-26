package com.example.bakingapp.Database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bakingapp.Database.entity.RecipeEntity;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert
    void insertRecipe(RecipeEntity recipeEntity);

    @Query("select * from recipe")
    List<RecipeEntity> selectRecipe();

    @Update
    void updateRecipe(RecipeEntity recipeEntity);

    @Delete
    void deleteRecipe(RecipeEntity recipeEntity);
}
