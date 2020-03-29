package com.frednm.baking_app.data.local;

import androidx.room.TypeConverter;

import com.frednm.baking_app.data.model.RecipeIngredient;
import com.frednm.baking_app.data.model.RecipeStep;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters {

    private static Gson gson = new Gson();
    private static Gson gsonTwo = new Gson();

    @TypeConverter
    public static List<RecipeIngredient> stringToRecipeIngredientList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<RecipeIngredient>>() {}.getType();
        return gsonTwo.fromJson(data, listType);
    }

    @TypeConverter
    public static String recipeIngredientListToString(List<RecipeIngredient> someObjects) {
        return gsonTwo.toJson(someObjects);
    }

    @TypeConverter
    public static List<RecipeStep> stringToRecipeStepList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<RecipeStep>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String recipeStepListToString(List<RecipeStep> someObjects) {
        return gson.toJson(someObjects);
    }
}
