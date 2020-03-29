package com.frednm.baking_app.di.module;

import com.frednm.baking_app.features.detail.DetailRecipeFragment;
import com.frednm.baking_app.features.detail.DetailRecipeStepFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract DetailRecipeFragment contributeDetailRecipeFragment();

    @ContributesAndroidInjector
    abstract DetailRecipeStepFragment contributeDetailRecipeStepFragment();
}
