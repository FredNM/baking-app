package com.frednm.baking_app.common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;


abstract public class BaseActivity extends DaggerAppCompatActivity {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.configureDagger();
        super.onCreate(savedInstanceState);
    }

    // Read second comment here (https://stackoverflow.com/questions/7621349/do-you-use-onpostcreate-method)
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void configureDagger(){
        AndroidInjection.inject(this);
    }


}
