package com.frednm.baking_app.features.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.frednm.baking_app.R;
import com.frednm.baking_app.common.base.BaseActivity;
import com.frednm.baking_app.databinding.FragmentDetailRecipeBinding;

import javax.inject.Inject;

public class DetailRecipeActivity extends BaseActivity implements DetailViewModel.Listeners {

    @Inject ViewModelProvider.Factory viewModelFactory;
    protected static DetailViewModel viewModel;

    public static final String EXTRA_RECIPEID = "extra_recipeId";
    private static final int DEFAULT_RECIPEID = -1;
    private DetailRecipeFragment detailRecipeFragment;
    private DetailRecipeStepFragment detailRecipeStepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(DetailViewModel.class);
        viewModel.implementedListener(this);

        setContentView(R.layout.activity_detail_recipe);

        Integer recipeId = getIntent().getIntExtra(EXTRA_RECIPEID, DEFAULT_RECIPEID);
        viewModel.loadDataWhenActivityStarts(recipeId);

        this.configureAndShowDetailRecipeFragment();
        this.configureAndShowDetailRecipeStepFragment();
    }

    @Override
    public void navigateToDetailRecipeStepActivity() {

        if (detailRecipeStepFragment != null && detailRecipeStepFragment.isVisible()) {
            detailRecipeStepFragment.executeBindingMethods();
        } else {
            Intent intent = new Intent(this, DetailRecipeStepActivity.class);
            startActivity(intent);
        }
    }


    private void configureAndShowDetailRecipeFragment(){
        detailRecipeFragment = (DetailRecipeFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_recipe);

        if (detailRecipeFragment == null) {
            detailRecipeFragment = DetailRecipeFragment.newInstance(viewModel);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_recipe, detailRecipeFragment)
                    .commit();
        }
    }

    private void configureAndShowDetailRecipeStepFragment(){
        detailRecipeStepFragment = (DetailRecipeStepFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_recipe_step);

        if (detailRecipeStepFragment == null && findViewById(R.id.frame_layout_recipe_step) != null) {
            // Create new main fragment
            detailRecipeStepFragment = DetailRecipeStepFragment.newInstance(viewModel);
            // Add it to FrameLayout container
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(R.id.frame_layout_recipe_step, detailRecipeStepFragment)
                    .commit();
        }
    }
}
