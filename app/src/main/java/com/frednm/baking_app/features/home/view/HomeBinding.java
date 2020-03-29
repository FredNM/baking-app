package com.frednm.baking_app.features.home.view;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.frednm.baking_app.data.model.Recipe;

import java.util.List;

public class HomeBinding {

    @BindingAdapter("itemsHome")
    public static void setItems (RecyclerView recyclerView,@NonNull List<Recipe> recipes) {
        if(!(recipes == null)) {
            if (recyclerView.getAdapter() instanceof HomeAdapter) {
                ((HomeAdapter) recyclerView.getAdapter()).updateData(recipes);
            }
        }
    }

}
