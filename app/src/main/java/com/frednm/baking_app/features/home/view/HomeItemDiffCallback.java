package com.frednm.baking_app.features.home.view;

import androidx.recyclerview.widget.DiffUtil;

import com.frednm.baking_app.data.model.Recipe;

import java.util.List;

public class HomeItemDiffCallback extends DiffUtil.Callback {

    private List<Recipe> oldList;
    private List<Recipe> newList;

    public HomeItemDiffCallback(List<Recipe> oldList, List<Recipe> newList) {
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
        return oldList.get(oldItemPosition).getId()== newList.get(newItemPosition).getId();
    }
}
