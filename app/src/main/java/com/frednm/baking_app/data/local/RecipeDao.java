package com.frednm.baking_app.data.local;

import android.util.Log;
import androidx.room.Dao;
import androidx.room.Query;

import com.frednm.baking_app.data.model.Recipe;

import java.util.List;

@Dao
public abstract class RecipeDao extends BaseDao<Recipe> {
    // TODO All the work

    // -- SELECT
    @Query("SELECT * FROM Recipe")
    public abstract List<Recipe> getRecipes();
    @Query("SELECT * FROM Recipe WHERE id = :id")
    public abstract Recipe getRecipe(Integer id);

    //For insertion, this is done only one time, so no need to delete old data and insert new ones, using a transaction
    public void saveRecipes(List<Recipe> recipes) {
        Log.d("RecipeDao", "Recipes are "+ recipes);
        if (recipes != null) {
            insert(recipes);
            Log.d("RecipeDao", "Recipes have data ");
        } else {
            Log.d("RecipeDao", "Recipes data are null");
        }
    }
}
