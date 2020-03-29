package com.frednm.baking_app.features.detail;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.frednm.baking_app.R;
import com.frednm.baking_app.common.base.BaseActivity;

public class DetailRecipeStepActivity extends BaseActivity {

    private DetailRecipeStepFragment detailRecipeStepFragment;

    private DetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe_step);

        this.viewModel = DetailRecipeActivity.viewModel ;

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
}
