package com.frednm.baking_app.features.detail;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.frednm.baking_app.data.model.RecipeStep;
import com.frednm.baking_app.databinding.FragmentDetailRecipeStepBinding;
import com.frednm.baking_app.features.detail.utils.ExoPlayerLoadControl;
import com.frednm.baking_app.features.widget.UpdateIngredientService;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRecipeStepFragment extends Fragment implements DetailViewModel.FragmentListeners{ // DetailViewModel.FragmentListeners<String>

    private static DetailViewModel viewModel;
    FragmentDetailRecipeStepBinding binding;

    // --- FOR EXOPLAYER
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private static final String BAKINGAPP = "BAKINGAPP";
    private static final String CURRENT_POSITION_TAG = "CURRENT_POSITION_TAG";
    private static final String PLAY_WHEN_READY_TAG= "PLAY_WHEN_READY_TAG";

    // --- CONSTRUCTORS
    public  DetailRecipeStepFragment() {}

    public static DetailRecipeStepFragment newInstance(DetailViewModel detailViewModel) {
        DetailRecipeStepFragment fragment = new DetailRecipeStepFragment();
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
        binding = FragmentDetailRecipeStepBinding.inflate(inflater, container, false);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.implementedFragmentListener(this);
        this.setUpWidgetServiceMethod();

        playerView = binding.stepVideoIv;

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 5 - Restore last buttonTag if possible
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(CURRENT_POSITION_TAG, 0);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_TAG, true);
        }
    } 

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CURRENT_POSITION_TAG, playbackPosition);
        outState.putBoolean(PLAY_WHEN_READY_TAG, playWhenReady);
    }

    // --- EXOPLAYER
    @Override
    public void onStart() {
        super.onStart();
        if ((Util.SDK_INT >= 24)&&(binding.stepVideoIv.getVisibility() == View.VISIBLE)) {
            viewModel.recipeStep.observe(this, this::runPlayer);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || player == null) &&(binding.stepVideoIv.getVisibility() == View.VISIBLE)) {
            viewModel.recipeStep.observe(this, this::runPlayer);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    // --- FOR THE EXOPLAYER
    private void initializePlayer(String url) {
        if (player == null) {
            DefaultTrackSelector trackSelector = new DefaultTrackSelector();
            trackSelector.setParameters(
                    trackSelector.buildUponParameters().setMaxVideoSizeSd());
            player = ExoPlayerFactory.newSimpleInstance(requireContext(), trackSelector, ExoPlayerLoadControl.loadControl);
            // ExoPlayerLoadControl.loadControl is expected to speed up video loading !
        }
        playerView.setPlayer(player);
        Uri uri = Uri.parse(url);
        MediaSource mediaSource = buildMediaSource(uri);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);

        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(requireContext(), BAKINGAPP);
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    private void runPlayer(RecipeStep recipeStep) {
        if (recipeStep != null) {
            if (recipeStep.getVideoURL() != null) {
                initializePlayer(recipeStep.getVideoURL());
            }
        }
    }

    @Override
    public void executeBindingMethods() {
        this.binding.setViewmodel(viewModel);
    }

    private void setUpWidgetServiceMethod(){
        String ingredientText = viewModel.getIngredientText();
        this.executeWidgetServiceMethod(ingredientText);
    }

    private void executeWidgetServiceMethod(@NonNull String ingredientText) {
        ArrayList<String> list = new ArrayList<>();
        list.add(ingredientText);
        UpdateIngredientService.startBakingService(requireContext(),list);
    }
}
