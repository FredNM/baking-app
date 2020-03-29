package com.frednm.baking_app.features.detail.domain;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.frednm.baking_app.data.model.Recipe;
import com.frednm.baking_app.data.repository.BakingRepository;


import javax.inject.Inject;

public class GetRecipeUseCase {

    public BakingRepository repository;

    @Inject
    public GetRecipeUseCase(BakingRepository repository) {
        this.repository = repository;
    }

    public LiveData<Recipe> invoke(Integer recipeId){
        return repository.getRecipe(recipeId);
       // return Transformations.map(repository.getRecipe(recipeId),this::transform);
    }

    // Useless so far, but could be interesting to apply something here
    private Recipe transform(Recipe data) {
        Log.d("GetRecipeUseCase", "Entering in transform");
        if (data != null) {
            Log.d("GetRecipeUseCase", "data are null ");
        }
        return data;
    }
}
