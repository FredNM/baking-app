package com.frednm.baking_app.features.home.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.frednm.baking_app.data.model.Recipe;
import com.frednm.baking_app.data.repository.BakingRepository;

import java.util.List;
import javax.inject.Inject;

public class GetRecipesUseCase {

    public BakingRepository repository;

    @Inject
    public GetRecipesUseCase(BakingRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Recipe>> invoke(){
        return Transformations.map(repository.getRecipes(),this::transform);
    }

    // Useless so far, but could be interesting to apply something here
    private List<Recipe> transform(List<Recipe> data) {
        return data;
    }
}
