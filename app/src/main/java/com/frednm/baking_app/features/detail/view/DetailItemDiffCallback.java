package com.frednm.baking_app.features.detail.view;

import androidx.recyclerview.widget.DiffUtil;

import com.frednm.baking_app.data.model.Recipe;
import com.frednm.baking_app.data.model.RecipeStep;

import java.util.List;

public class DetailItemDiffCallback extends DiffUtil.Callback {

    private List<RecipeStep> oldList;
    private List<RecipeStep> newList;

    public DetailItemDiffCallback(List<RecipeStep> oldList, List<RecipeStep> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId()
                && oldList.get(oldItemPosition).getDescription()== newList.get(newItemPosition).getDescription()
                && oldList.get(oldItemPosition).getVideoURL()== newList.get(newItemPosition).getVideoURL();
    }
}
