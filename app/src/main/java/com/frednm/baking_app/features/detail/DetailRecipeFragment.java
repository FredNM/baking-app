package com.frednm.baking_app.features.detail;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.frednm.baking_app.databinding.FragmentDetailRecipeBinding;
import com.frednm.baking_app.features.detail.view.DetailAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRecipeFragment extends Fragment {

    private static DetailViewModel viewModel;

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

        return binding.getRoot();
    }

    private void configureRecyclerView() {
        binding.fragmentDetailRecyclerView.setAdapter(new DetailAdapter(viewModel));
    }
}
