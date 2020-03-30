package com.frednm.baking_app.features.detail;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.frednm.baking_app.R;
import com.frednm.baking_app.common.base.BaseActivity;

public class DetailRecipeStepActivity extends BaseActivity {

    private DetailRecipeStepFragment detailRecipeStepFragment;
    public static final String EXTRA_FROMWIDGET = "EXTRA_FROMWIDGET";
    public static final boolean DEFAULT_FROMWIDGET = false;

    private DetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe_step);

        this.viewModel = DetailRecipeActivity.viewModel ;
        boolean fromWidget = getIntent().getBooleanExtra(EXTRA_FROMWIDGET, DEFAULT_FROMWIDGET);
        if (fromWidget) { // JUST FOR SPECIFIC CASE OF WIDGET. fromWiget is get from RecipeWidgetProvider
            this.setSpecificViewModel();
        }

        this.configureAndShowDetailRecipeStepFragment();
    }

    private void configureAndShowDetailRecipeStepFragment(){
        detailRecipeStepFragment = (DetailRecipeStepFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_recipe_step);

        if (detailRecipeStepFragment == null) {
            detailRecipeStepFragment = DetailRecipeStepFragment.newInstance(viewModel);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(R.id.frame_layout_recipe_step, detailRecipeStepFragment)
                    .commit();
        }
    }

    private void setSpecificViewModel(){
        String ingredientText = this.viewModel.getIngredientText(); // Always contains the ingredientText, since it was
        // 'forced' to execute already when showing DetailRecipeFragment. See the onClickAddWidget() method.
        DetailViewModel detailViewModel = new DetailViewModel();
        detailViewModel.setIngredientIsSelected(true);
        detailViewModel.setRecipeStepIsSelected(false);
        detailViewModel.setShowFABBack(false);
        detailViewModel.setShowFABForth(false);
        detailViewModel.setIngredientText(ingredientText);
        this.viewModel = detailViewModel;
    }
}
