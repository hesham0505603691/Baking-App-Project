package com.hesham.baking;

import android.os.Bundle;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;

public class StepFrag extends Fragment {
    @BindView(R.id.step_description)
    TextView description;
    @BindView(R.id.next_btn)
    ImageButton next_button;
    @BindView(R.id.previous_btn)
    ImageButton previous_button;
    @BindView(R.id.thumbnail_img)
    ImageView thumbnail_img;

    private SimpleExoPlayer player;
    private PlayerView playerView;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    public static final String MORE_INDEX = "index";
    private List<Baking.steps> mStepList;
    private int index;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    private static final String EXTRA_PLAYBACKPOSIITON = "playbackposition";
    private static final String EXTRA_CURRENTWINDOW = "currentwindow";
    private static final String EXTRA_PLAYWHENREADY = "playwhenready";
    boolean loadPlayer = false;
    public String VideoLink;

    ClickListener mCallback;

    public interface ClickListener {
        void next(View view);
        void previous(View view);
    }


    public StepFrag(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            mStepList = savedInstanceState.getParcelableArrayList(MainActivity.MORE_STEPS);
            index = savedInstanceState.getInt(MORE_INDEX,0);
            playbackPosition = savedInstanceState.getLong(EXTRA_PLAYBACKPOSIITON,0);
            currentWindow = savedInstanceState.getInt(EXTRA_CURRENTWINDOW,0);
            playWhenReady = savedInstanceState.getBoolean(EXTRA_PLAYWHENREADY, false);
        }



        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);

        playerView = rootView.findViewById(R.id.video_view);


        shouldPlay();

        setContent();

        navigationLoad();

        return rootView;
    }

    private void setContent()
    {
        if(mStepList != null) {
            description.setText(mStepList.get(index).description);


                if(mStepList.get(index).thumbnailURL.length() > 0) {

                    Picasso.get()
                            .load(mStepList.get(index).thumbnailURL)
                            .into(thumbnail_img, new Callback() {
                                @Override
                                public void onSuccess() {
                                }
                                @Override
                                public void onError(Exception e) {
                                    thumbnail_img.setVisibility(View.GONE);
                                }
                            });
                }
                else
                {
                    thumbnail_img.setVisibility(View.GONE);
                }
            }
    }

    private void navigationLoad()
    {
        if(index == 0) previous_button.setVisibility(View.INVISIBLE);
        if(index > 0) previous_button.setVisibility(View.VISIBLE);
        if(index == mStepList.size() -1) next_button.setVisibility(View.INVISIBLE);
        if(index < mStepList.size() -1 ) next_button.setVisibility(View.VISIBLE);

    }

    public void shouldPlay()
    {
        // VideoLink = mStepList.get(index).videoURL.length() > 0 ?
         //       mStepList.get(index).videoURL : mStepList.get(index).thumbnailURL;

        VideoLink = mStepList.get(index).videoURL;

        loadPlayer = VideoLink.length() > 0;

       // if(!loadPlayer) playerView.setVisibility(View.GONE);
        if(!loadPlayer){
            playerView.setVisibility(View.GONE);
            releasePlayer();
        }
        else
        {
            playerView.setVisibility(View.VISIBLE);
            initializePlayer();
        }
    }

    public void setData(List<Baking.steps> steps,int i){
        mStepList = steps;
        index = i;
        playbackPosition = 0;
    }

    public void setloadData(List<Baking.steps> steps,int i)
    {
        index = i;

        description.setText(mStepList.get(index).description);
        playbackPosition = 0;
        shouldPlay();
        navigationLoad();
    }

    public void loadNext()
    {

        if(index < mStepList.size() -1) index++;
        setContent();

        if(index > 0) previous_button.setVisibility(View.VISIBLE);
        if(index == mStepList.size() -1) next_button.setVisibility(View.INVISIBLE);

        shouldPlay();
    }

    public void loadPrevious()
    {

        if(index > 0) index--;
        setContent();

        if(index < mStepList.size() - 1) next_button.setVisibility(View.VISIBLE);
        if(index == 0) previous_button.setVisibility(View.INVISIBLE);

        shouldPlay();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (ClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MainActivity.MORE_STEPS, (ArrayList) mStepList);
        outState.putInt(MORE_INDEX,index);
        if(player != null) {
            outState.putLong(EXTRA_PLAYBACKPOSIITON,  player.getCurrentPosition());
            outState.putInt(EXTRA_CURRENTWINDOW, player.getCurrentWindowIndex());
            outState.putBoolean(EXTRA_PLAYWHENREADY,  player.getPlayWhenReady());
        }
        super.onSaveInstanceState(outState);
    }

    private void initializePlayer() {
            if (loadPlayer) {

                if (player == null) {
                    TrackSelection.Factory adaptiveTrackSelectionFactory =
                            new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

                    player = ExoPlayerFactory.newSimpleInstance(
                            new DefaultRenderersFactory(getContext()),
                            new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                            new DefaultLoadControl());
                    playerView.setPlayer(player);

                }

                MediaSource mediaSource = buildMediaSource(Uri.parse(VideoLink));

                if(playbackPosition != 0)
                {
                    player.prepare(mediaSource, false, true);
                    player.seekTo(playbackPosition);
                    player.setPlayWhenReady(playWhenReady);
                    player.getPlaybackState();
                }
                else {
                    player.prepare(mediaSource, true, false);
                    player.seekTo(playbackPosition);
                    player.setPlayWhenReady(playWhenReady);
                    player.getPlaybackState();
                }

            }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
           // initialized_Exo = false;

        }
    }

    private MediaSource buildMediaSource(Uri uri) {

        String userAgent = "exoplayer-codelab";

        if (uri.getLastPathSegment().contains("mp3") || uri.getLastPathSegment().contains("mp4")) {
            return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri);
        } else if (uri.getLastPathSegment().contains("m3u8")) {
            return new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri);
        } else {
            DashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(
                    new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER));
            DataSource.Factory manifestDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent);
            return new DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).
                    createMediaSource(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23 ) {
            shouldPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 ) {
            shouldPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUiFullScreen() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {

            hideSystemUiFullScreen();
        }
        else {
            hideSystemUi();
        }
    }

}
