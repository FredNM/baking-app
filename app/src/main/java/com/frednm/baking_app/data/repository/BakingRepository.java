package com.frednm.baking_app.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.frednm.baking_app.data.local.RecipeDao;
import com.frednm.baking_app.data.model.Recipe;
import com.frednm.baking_app.data.remote.BakingDataSource;
import com.frednm.baking_app.data.repository.utils.NetworkBoundResource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

@Singleton
public class BakingRepository {

    private BakingDataSource bakingDataSource;
    private RecipeDao recipeDao;
    private Context context;
    private List<Recipe> recipeList = new ArrayList<>();

    MutableLiveData<Recipe> recipe = new MutableLiveData<>();

    @Inject
    public BakingRepository(BakingDataSource bakingDataSource, RecipeDao recipeDao, Context context) {
        this.bakingDataSource = bakingDataSource;
        this.recipeDao = recipeDao;
        this.context = context;
    }

    public LiveData<List<Recipe>> getRecipes(){
        return new NetworkBoundResource<List<Recipe>,List<Recipe>>(){

            @Override
            protected List<Recipe> processResponse(List<Recipe> response) {
                return response;
            }

            @Override
            protected void saveCallResults(@NonNull List<Recipe> items) {
                recipeDao.saveRecipes(items);
            }

            @Override
            protected Boolean shouldFetch(@Nullable List<Recipe> data) {
                return data==null || data.isEmpty() ;
            }

            @NonNull
            @Override
            protected List<Recipe> loadFromDb() {
                recipeList = recipeDao.getRecipes();
                return recipeList;
            }

            @NonNull
            @Override
            protected Call<List<Recipe>> createCallAsync() {
                return bakingDataSource.fetchRecipes();
            }
        }.asLiveData();
    }

    private void setRecipe(Integer recipeId) {
        recipe.setValue(recipeList.get(recipeId-1));
    }

    public LiveData<Recipe> getRecipe(Integer recipeId){
        this.setRecipe(recipeId);
        return recipe;
     }
}
