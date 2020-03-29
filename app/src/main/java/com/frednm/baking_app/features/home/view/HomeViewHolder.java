package com.frednm.baking_app.features.home.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frednm.baking_app.data.model.Recipe;
import com.frednm.baking_app.databinding.ItemActivityHomeBinding;
import com.frednm.baking_app.features.home.HomeViewModel;


public class HomeViewHolder extends RecyclerView.ViewHolder {

    private ItemActivityHomeBinding binding;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ItemActivityHomeBinding.bind(itemView);
    }

    protected void bindTo(Recipe recipe, HomeViewModel viewModel) {
        binding.setRecipe(recipe);
        binding.setViewmodel(viewModel);
    }
}
