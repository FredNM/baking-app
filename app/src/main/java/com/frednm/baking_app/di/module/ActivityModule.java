package com.frednm.baking_app.di.module;


import com.frednm.baking_app.common.base.BaseActivity;
import com.frednm.baking_app.features.detail.DetailRecipeActivity;
import com.frednm.baking_app.features.detail.DetailRecipeStepActivity;
import com.frednm.baking_app.features.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract HomeActivity homeActivity();

    @ContributesAndroidInjector
    abstract DetailRecipeActivity detailRecipeActivity();

    @ContributesAndroidInjector
    abstract DetailRecipeStepActivity detailRecipeStepActivity();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract BaseActivity baseActivity();
}
