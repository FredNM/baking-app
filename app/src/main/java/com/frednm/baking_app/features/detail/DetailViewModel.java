package com.frednm.baking_app.features.detail;

import android.util.SparseArray;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.frednm.baking_app.data.model.Recipe;
import com.frednm.baking_app.data.model.RecipeIngredient;
import com.frednm.baking_app.data.model.RecipeStep;
import com.frednm.baking_app.features.detail.domain.GetRecipeUseCase;
import com.frednm.baking_app.features.detail.utils.TextCreator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DetailViewModel extends ViewModel {

    public interface Listeners { // implemented in DetailRecipeActivity
        void navigateToDetailRecipeStepActivity();
    }
    private WeakReference<Listeners> callback;

    public interface FragmentListeners {
        void executeBindingMethods();
    }
    private WeakReference<FragmentListeners> callbackFragment;


    private GetRecipeUseCase getRecipeUseCase;
    private int recipeId;
    private Integer recipeStepId;

    private View previousView;

    private TextCreator textCreator = new TextCreator();
    public String ingredientText ;

    public boolean ingredientIsSelected;
    public boolean recipeStepIsSelected;
    public boolean recipeStepContainsVideo;
    public boolean showFABForth ;
    public boolean showFABBack ;


    // 1-- variables used by DetailRecipeFragment
    private MediatorLiveData<Recipe> _recipe = new MediatorLiveData<>();
    public LiveData<Recipe> recipe = _recipe; // Likely useles, recipe not observed on layout
    private LiveData<Recipe> recipeSource = new MutableLiveData<>();

    private MutableLiveData<List<RecipeStep>> _recipeSteps = new MutableLiveData<>();
    public LiveData<List<RecipeStep>> recipeSteps = _recipeSteps;
    private SparseArray<RecipeStep> recipeStepsData = new SparseArray<>();
    private  List<RecipeIngredient> recipeIngredientsData = new ArrayList<>();

    // 2-- variables used by DetailRecipeStepFragment
    private MutableLiveData<RecipeStep> _recipeStep = new MutableLiveData<>();
    public LiveData<RecipeStep> recipeStep = _recipeStep;
    private RecipeStep recipeStepData;

    // --- CONSTRUCTOR
    @Inject
    public DetailViewModel(GetRecipeUseCase getRecipeUseCase) {
        this.getRecipeUseCase = getRecipeUseCase;
    }

    // --- GETTER
    public String getIngredientText() {  // Needed in the DetailRecipeStepFragment, for configuration of WidgetService
        return ingredientText;
    }

    // USER ACTIONS ON RECIPE FRAGMENT
    public void loadDataWhenActivityStarts(Integer id){
        recipeId = id;
        getDetailRecipe();
    }


    public void userClicksOnIngredient(View view){
        ingredientIsSelected = true;
        recipeStepIsSelected = false;
        showFABForth = false; // show off FAB
        showFABBack = false;

        // Set the view State
        this.handleViewState(view);

        //Configurer ensuite le texte
        ingredientText = textCreator.showIngredients(recipeIngredientsData);
        this.callback.get().navigateToDetailRecipeStepActivity();
    }

    public void userClicksOnStep(View view, RecipeStep recipeStep){
        recipeStepId = recipeStep.getId();
        this.callback.get().navigateToDetailRecipeStepActivity();
        this.enteringNewRecipeStep(view, recipeStepId);
    }

    public void userClicksFABForth(View view) {
        recipeStepId = recipeStepId + 1 ;
        this.callbackFragment.get().executeBindingMethods();
        this.enteringNewRecipeStep(view, recipeStepId);
    }

    public void userClicksFABBack(View view) {
        recipeStepId = recipeStepId - 1 ;
        this.callbackFragment.get().executeBindingMethods();
        this.enteringNewRecipeStep(view, recipeStepId);
    }

    // METHODS TO SATISFY USER ACTIONS
    private void getDetailRecipe() {
        _recipe.removeSource(recipeSource);
        recipeSource = getRecipeUseCase.invoke(recipeId);
        this.treatDataRecipe(recipeSource.getValue());
        this.treatDataRecipeStep(recipeStepsData);
    }

    private void treatDataRecipe(Recipe resource) {
        if (resource != null) {
            for(int i=0;i<resource.getSteps().size();i++){
                this.recipeStepsData.put(resource.getSteps().get(i).getId(),resource.getSteps().get(i));
            }
            this.recipeIngredientsData = resource.getIngredients();
            this._recipe.setValue(resource);
        }
    }

    private void treatDataRecipeStep(SparseArray<RecipeStep> resource) {
        this._recipeSteps.setValue(convertSparseArrayToList(resource));
    }

    private List<RecipeStep> convertSparseArrayToList(SparseArray<RecipeStep> sparseArray) {
        List<RecipeStep> list = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            list.add(sparseArray.get(sparseArray.keyAt(i)));
        }
        return list;
    }

    private void enteringNewRecipeStep(View view, Integer recipeStepId) {
        this.settingBooleanAttrForCustomBinding(recipeStepId);
        this.handleViewState(view);
        recipeStepData = recipeStepsData.get(recipeStepId) ;
        _recipeStep.setValue(recipeStepData);
        if (recipeStepData.getVideoURL() == null || recipeStepData.getVideoURL().isEmpty()) {
            recipeStepContainsVideo = false;
        } else {
            recipeStepContainsVideo = true;
        }
    }

    private void handleViewState(View view) {
        if (previousView != null) {
            previousView.setSelected(false);
        }
        view.setSelected(true);
        previousView = view;
    }

    private void settingBooleanAttrForCustomBinding(Integer recipeId) {
        ingredientIsSelected = false;
        recipeStepIsSelected = true;
        showFABForth = true;
        showFABBack = true;

        if (recipeStepsData.indexOfKey(recipeId+1) < 0) {
            showFABForth = false;
        }
        if (recipeStepsData.indexOfKey(recipeId-1) < 0) { // A priori tous les recipeStepId commencent à 1 !
            showFABBack = false;
        }
    }

    public void implementedListener(DetailViewModel.Listeners callback) {
        this.callback  = new WeakReference<> (callback);
    }

    public void implementedFragmentListener(DetailViewModel.FragmentListeners callbackFragment) {
        this.callbackFragment  = new WeakReference<>(callbackFragment);
    }
}
