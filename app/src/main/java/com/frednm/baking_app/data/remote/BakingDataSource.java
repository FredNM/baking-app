package com.frednm.baking_app.data.remote;


import com.frednm.baking_app.data.model.Recipe;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class BakingDataSource {

    private BakingService bakingService;

    @Inject
    public BakingDataSource(BakingService bakingService) {
        this.bakingService = bakingService;
    }

    public Call<List<Recipe>> fetchRecipes() {
        return bakingService.fetchRecipes("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
    }

    public Call<Recipe> fetchRecipe() {
        return null;
    } // fictive method, just to fill something !
}
