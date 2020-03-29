package com.frednm.baking_app.features.home.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.frednm.baking_app.R;
import com.frednm.baking_app.data.model.Recipe;
import com.frednm.baking_app.features.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private List<Recipe> recipes = new ArrayList<>();
    private HomeViewModel viewModel;

    public HomeAdapter(HomeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.bindTo(recipes.get(position), viewModel);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


    public void updateData(List<Recipe> items) {
        HomeItemDiffCallback diffCallback = new HomeItemDiffCallback(recipes, items);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        recipes.clear();
        recipes.addAll(items);
        diffResult.dispatchUpdatesTo(this);
    }
}
