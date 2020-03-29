package com.frednm.baking_app.features.detail.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frednm.baking_app.data.model.RecipeStep;
import com.frednm.baking_app.databinding.ItemFragmentDetailRecipeBinding;
import com.frednm.baking_app.features.detail.DetailViewModel;

public class DetailViewHolder extends RecyclerView.ViewHolder {

    private ItemFragmentDetailRecipeBinding binding;

    public DetailViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ItemFragmentDetailRecipeBinding.bind(itemView);
    }

    protected void bindTo(RecipeStep recipeStep, DetailViewModel viewModel) {
        binding.setRecipeStep(recipeStep);
        binding.setViewmodel(viewModel);
    }
}
