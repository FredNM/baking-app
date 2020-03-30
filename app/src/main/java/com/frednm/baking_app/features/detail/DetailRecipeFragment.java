package com.frednm.baking_app.features.detail;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.frednm.baking_app.R;
import com.frednm.baking_app.databinding.FragmentDetailRecipeBinding;
import com.frednm.baking_app.features.detail.view.DetailAdapter;
import com.frednm.baking_app.features.widget.UpdateIngredientService;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRecipeFragment extends Fragment {

    private static DetailViewModel viewModel;

    public static final String FOR_WIDGET = "FOR_WIDGET";

    FragmentDetailRecipeBinding binding;

    // --- CONSTRUCTORS
    public  DetailRecipeFragment() {}

    public static DetailRecipeFragment newInstance(DetailViewModel detailViewModel) {
        DetailRecipeFragment fragment = new DetailRecipeFragment();
        fragment.setViewModel(detailViewModel);
        return fragment;
    }

    // -- SETTERS
    public void setViewModel(DetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailRecipeBinding.inflate(inflater, container, false);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);
        this.configureRecyclerView();
        this.onClickUpdateWidget();

        return binding.getRoot();
    }

    private void configureRecyclerView() {
        binding.fragmentDetailRecyclerView.setAdapter(new DetailAdapter(viewModel));
    }

    public void onClickUpdateWidget() {
        // 1- save recipeName in SharedPreferences
        SharedPreferences sharedPref = requireActivity().getSharedPreferences(FOR_WIDGET, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.pref_widget_label_recipeId),viewModel.getRecipeId());
        editor.putString(getString(R.string.pref_widget_label_recipeName),viewModel.getRecipeName());
        editor.apply();
        // 2- notifyDataSetChanged
        UpdateIngredientService.startBakingService(requireContext());
        // 3- set ingredientText, so that even if users does not open the Recipe Ingredient, when widget will be started,
        // ingredient text will be available
        viewModel.forceSetIngredientText();
    }

}
