package com.frednm.baking_app.features.detail.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.frednm.baking_app.R;
import com.frednm.baking_app.data.model.RecipeStep;
import com.frednm.baking_app.features.detail.DetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailViewHolder> {

    private List<RecipeStep> recipeSteps = new ArrayList<>();
    private DetailViewModel viewModel;

    public DetailAdapter(DetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_detail_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        holder.bindTo(recipeSteps.get(position), viewModel);
    }

    @Override
    public int getItemCount() {
        return recipeSteps.size();
    }


    public void updateData(List<RecipeStep> items) {
        DetailItemDiffCallback diffCallback = new DetailItemDiffCallback(recipeSteps, items);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        recipeSteps.clear();
        recipeSteps.addAll(items);
        diffResult.dispatchUpdatesTo(this);
    }
}
