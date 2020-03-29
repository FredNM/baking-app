package com.frednm.baking_app.features.home;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.frednm.baking_app.R;
import com.frednm.baking_app.common.base.BaseActivity;
import com.frednm.baking_app.databinding.ActivityHomeBinding;
import com.frednm.baking_app.features.detail.DetailRecipeActivity;
import com.frednm.baking_app.features.home.view.HomeAdapter;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity implements HomeViewModel.Listeners {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @NonNull
    public static HomeViewModel viewModel;

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);
        viewModel.implementedListener(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        this.configureRecyclerView();
    }

    private void configureRecyclerView() {
        binding.activityHomeRecyclerView.setAdapter(new HomeAdapter(viewModel));
    }

    @Override
    public void navigateToDetailRecipeActivity(Integer recipeId) {
        Intent intent = new Intent(this, DetailRecipeActivity.class);
        intent.putExtra(DetailRecipeActivity.EXTRA_RECIPEID, recipeId);
        startActivity(intent);
    }
}
