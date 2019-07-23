package com.hesham.baking;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;


public class StepsFrag extends Fragment  {

    @BindView(R.id.img_text)
    TextView textImage;
    @BindView(R.id.toolbarImage)
    ImageView toolbarImage;
    @BindView(R.id.rv_steps)
    RecyclerView mBakingSteps_RV;

    private String title;
    private int img_Resource;
    private StepsAdapter mStepsAdapter;
    private List<Baking.steps> mStepList;

    public StepsFrag(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            mStepList = savedInstanceState.getParcelableArrayList(MainActivity.MORE_STEPS);
        }

        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        ButterKnife.bind(this, rootView);
        textImage.setText(title);
        toolbarImage.setImageResource(img_Resource);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBakingSteps_RV.getContext(),
                layoutManager.getOrientation());
        mBakingSteps_RV.setFocusable(false);
        mBakingSteps_RV.setLayoutManager(layoutManager);
        mBakingSteps_RV.setHasFixedSize(true);
        mBakingSteps_RV.setAdapter(mStepsAdapter);
        mBakingSteps_RV.addItemDecoration(dividerItemDecoration);

        return rootView;
    }

    public void setData(List<Baking.steps> steps, StepsAdapter stepsAdapter,String text_title, int image) {
        mStepList = steps;
        mStepsAdapter = stepsAdapter;
        title = text_title;
        img_Resource = image;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MainActivity.MORE_STEPS, (ArrayList) mStepList);
        super.onSaveInstanceState(outState);
    }
}
