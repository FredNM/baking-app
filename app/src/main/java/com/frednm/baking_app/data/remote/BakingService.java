package com.frednm.baking_app.data.remote;

import com.frednm.baking_app.data.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface BakingService {

    @GET
    Call<List<Recipe>> fetchRecipes(@Url String url);
}
