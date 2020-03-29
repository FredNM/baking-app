package com.frednm.baking_app.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.frednm.baking_app.data.model.Recipe;


@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class BakingAppDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile BakingAppDatabase INSTANCE;

    // --- DAO ---
    public abstract RecipeDao recipeDao();

}