package com.frednm.baking_app.features.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.frednm.baking_app.data.model.Recipe;
import com.frednm.baking_app.features.home.domain.GetRecipesUseCase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    public interface Listeners {
        void navigateToDetailRecipeActivity(Integer recipeId);
    }
    private WeakReference<Listeners> callback;

    private GetRecipesUseCase getRecipesUseCase;

    // FOR DATA SHOWN ON UI
    private MediatorLiveData<List<Recipe>> _recipes = new MediatorLiveData<>();
    public LiveData<List<Recipe>> recipes = _recipes;
    private LiveData<List<Recipe>> recipesSource = new MutableLiveData<>();
    public List<Recipe> recipesData = new ArrayList<>();

    // --- CONSTRUCTOR
   @Inject
    public HomeViewModel(GetRecipesUseCase getRecipesUseCase) {
        this.getRecipesUseCase = getRecipesUseCase;
        getRecipes();
    }

    public void userClicksOnItem(Recipe recipe) {
        this.callback.get().navigateToDetailRecipeActivity(recipe.getId());
    }

    //  --- METHODS USED FOR USER ACTIONS
    private void getRecipes () {
        _recipes.removeSource(recipesSource);
        recipesSource = getRecipesUseCase.invoke();
        _recipes.addSource(recipesSource, this::treatData);
    }

    private void treatData(List<Recipe> recipes) {
        recipesData = recipes;
        this._recipes.setValue(recipes);
    }

    public void implementedListener(Listeners callback) { // call in onCreate of HomeActivity, to initialise callback with its implementation present in HomeActivity
        this.callback  = new WeakReference<> (callback);
    }
}
