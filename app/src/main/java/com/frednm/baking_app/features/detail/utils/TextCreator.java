package com.frednm.baking_app.features.detail.utils;

import android.text.TextUtils;

import com.frednm.baking_app.data.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class TextCreator {
    public String showIngredients(List<RecipeIngredient> recipeIngredients) {
        List<String> paragraph = new ArrayList<>();
        for(int i=0;i<recipeIngredients.size();i++){
            String sentence = "   -  " + recipeIngredients.get(i).getIngredient() + " : " + recipeIngredients.get(i).getQuantity() + " " + recipeIngredients.get(i).getMeasure();
            paragraph.add(sentence);
        }
        return TextUtils.join("\n", paragraph);
    }
}
