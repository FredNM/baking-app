package com.frednm.baking_app.features.detail.view;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.frednm.baking_app.data.model.Recipe;
import com.frednm.baking_app.data.model.RecipeStep;
import com.frednm.baking_app.features.home.view.HomeAdapter;

import java.util.List;

public class DetailBinding {

    @BindingAdapter("itemsDetail")
    public static void setItems(RecyclerView recyclerView, @NonNull List<RecipeStep> recipeSteps) {
        if (!(recipeSteps == null)) {
            if (recyclerView.getAdapter() instanceof DetailAdapter) {
                ((DetailAdapter) recyclerView.getAdapter()).updateData(recipeSteps);
            }
        }
    }

    @BindingAdapter("showWhenEmptyList")
    public static void showMessageErrorWhenEmptyList(View view, RecipeStep recipeStep) {
        if (recipeStep == null ) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("handleVisibility")
    public static void handleVisibilityState(View view, Boolean selected) {
        if (selected) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"handleVisibility", "handleVisibilityII"})
    public static void handleVisibilityIIState(View view, Boolean selected, Boolean containsVideo) {
        if (selected && containsVideo) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
