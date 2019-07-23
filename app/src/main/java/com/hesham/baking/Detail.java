package com.hesham.baking;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class Detail extends AppCompatActivity implements StepFrag.ClickListener, StepsAdapter.ListItemClickListener{

    private StepsAdapter stepsAdapt;
    private List<Baking.ingredients> list_ingredients;
    private List<Baking.steps> list_steps;
    public static final int VIEW_INGREDIENTS = 1;
    public static final int VIEW_STEP = 2;
    public static final int VIEW_STEPS = 3;
    private String title;
    private int imgRes;
    private static final String VIEW_MODE = "view_mode";
    private int view_mode;
    private static final int VIEW_SINGLE = 0;
    private static final int VIEW_DOUBLE = 1;
    private StepFrag stepFrag;
    private StepsFrag stepsFrag;
    private IngredientsFrag ingredientsFrag;
    public static final String VIEW = "view";
    public static final String IS_PHONE = "phone";
    public static final String INDEX = "index";
    private int view_type;
    private boolean isPhone;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        list_ingredients = new ArrayList<>();
        list_steps = new ArrayList<>();
        isPhone = getResources().getBoolean(R.bool.is_phone);
        index = 0;

        if(savedInstanceState != null)
        {
            list_ingredients = savedInstanceState.getParcelableArrayList(MainActivity.MORE_INGREDIENTS);
            list_steps = savedInstanceState.getParcelableArrayList(MainActivity.MORE_STEPS);
            title = savedInstanceState.getString(MainActivity.MORE_TITLE);
            imgRes = savedInstanceState.getInt(MainActivity.MORE_IMG, 0);
            view_type = savedInstanceState.getInt(VIEW);
            isPhone = savedInstanceState.getBoolean(IS_PHONE);
            index = savedInstanceState.getInt(INDEX);
            view_mode = savedInstanceState.getInt(VIEW_MODE);

            stepsAdapt = new StepsAdapter(list_steps,this);
        }
        else
            {
            Intent intent = getIntent();
            if(intent.hasExtra(MainActivity.MORE_STEPS)) {
                list_steps = intent.getParcelableArrayListExtra(MainActivity.MORE_STEPS);
                list_ingredients = intent.getParcelableArrayListExtra(MainActivity.MORE_INGREDIENTS);
                title = intent.getStringExtra(MainActivity.MORE_TITLE);
                imgRes = intent.getIntExtra(MainActivity.MORE_IMG, 0);
                view_type = VIEW_STEPS;
            }

        }

        if(!isPhone)
        {
            if(savedInstanceState == null) {
                stepsFrag = new StepsFrag();
                stepFrag = new StepFrag();
                stepsAdapt = new StepsAdapter(list_steps, this);
                stepFrag = new StepFrag();
                FragmentManager fragmentManager = getSupportFragmentManager();
                stepsFrag.setData(list_steps, stepsAdapt, title, imgRes);
                stepFrag.setData(list_steps, index);

                fragmentManager.beginTransaction()
                            .add(R.id.steps_container, stepsFrag)
                            .add(R.id.steps_details, stepFrag)
                            .commit();
            }
            view_mode = VIEW_DOUBLE;
            view_type = VIEW_STEP;
            stepsFrag.setData(list_steps, stepsAdapt,title, imgRes);

        }
        else
            {
                if(view_type == VIEW_STEPS) {
                    if(savedInstanceState == null) {
                        stepsFrag = new StepsFrag();
                        stepsAdapt = new StepsAdapter(list_steps,this);

                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .add(R.id.steps_container, stepsFrag)
                                .commit();
                    }

                    stepsFrag.setData(list_steps, stepsAdapt,title, imgRes);
                }
                view_mode = VIEW_SINGLE;

            }



         ActionBar actionBar = this.getSupportActionBar();
         if(actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if(view_mode == VIEW_SINGLE) {
            if (view_type == VIEW_STEPS) {
               index = clickedItemIndex;
               if (stepFrag == null) stepFrag = new StepFrag();
               FragmentManager fragmentManager = getSupportFragmentManager();
               stepFrag.setData(list_steps, index);
               fragmentManager.beginTransaction()
                       .replace(R.id.steps_container, stepFrag)
                       .commit();

               view_type = VIEW_STEP;
           }
        }
        else
        {
            index = clickedItemIndex;
            if (view_type == VIEW_INGREDIENTS) {
                if (stepFrag == null) stepFrag = new StepFrag();
                FragmentManager fragmentManager = getSupportFragmentManager();
                stepFrag.setData(list_steps, index);
                fragmentManager.beginTransaction()
                        .replace(R.id.steps_details, stepFrag)
                        .commit();
                view_type = VIEW_STEP;
            }
            else
            {
                stepFrag.setloadData(list_steps, index);
            }

        }
    }

    public void onClick_Ingredient(View v) {

        if(view_mode == VIEW_SINGLE) {
            if (view_type == VIEW_STEPS) {
                if (ingredientsFrag == null) ingredientsFrag = new IngredientsFrag();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ingredientsFrag.setData(list_ingredients);
                fragmentManager.beginTransaction()
                        .replace(R.id.steps_container, ingredientsFrag)
                        .commit();

                view_type = VIEW_INGREDIENTS;
            }
        }
        else
           {
            if (view_type == VIEW_STEP) {
                if (ingredientsFrag == null) ingredientsFrag = new IngredientsFrag();

                FragmentManager fragmentManager = getSupportFragmentManager();
                ingredientsFrag.setData(list_ingredients);
                ingredientsFrag.setFocus();

                fragmentManager.beginTransaction()
                        .replace(R.id.steps_details, ingredientsFrag)
                        .commit();

                view_type = VIEW_INGREDIENTS;
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled()) {
            if(view_mode == VIEW_SINGLE)
            {
                if(view_type == VIEW_STEP || view_type == VIEW_INGREDIENTS)
                {
                    if(stepsFrag == null) stepsFrag = new StepsFrag();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    stepsFrag.setData(list_steps, stepsAdapt,title, imgRes);

                    fragmentManager.beginTransaction()
                            .replace(R.id.steps_container, stepsFrag)
                            .commit();

                    view_type = VIEW_STEPS;
                    return true;
                }
                else
                {
                    NavUtils.navigateUpFromSameTask(this);
                    return  true;
                }
            }
            else
            {
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            if(view_mode == VIEW_SINGLE)
            {
                if(view_type == VIEW_STEP || view_type == VIEW_INGREDIENTS)
                {
                    if(stepsFrag == null) stepsFrag = new StepsFrag();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    stepsFrag.setData(list_steps, stepsAdapt,title, imgRes);

                    fragmentManager.beginTransaction()
                            .replace(R.id.steps_container, stepsFrag)
                            .commit();

                        view_type = VIEW_STEPS;
                    return true;
                }
                else
                {
                    NavUtils.navigateUpFromSameTask(this);
                }
            }
            else
            {
                NavUtils.navigateUpFromSameTask(this);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
       outState.putParcelableArrayList(MainActivity.MORE_INGREDIENTS, (ArrayList) list_ingredients);
        outState.putParcelableArrayList(MainActivity.MORE_STEPS, (ArrayList) list_steps);
        outState.putString(MainActivity.MORE_TITLE,title);
        outState.putInt(MainActivity.MORE_IMG, imgRes);
        outState.putInt(VIEW,view_type);
        outState.putBoolean(IS_PHONE,isPhone);
        outState.putInt(INDEX,index);
        outState.putInt(VIEW_MODE,view_mode);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof StepFrag) stepFrag = (StepFrag) fragment;
        if(fragment instanceof StepsFrag) stepsFrag = (StepsFrag) fragment;
        if(fragment instanceof IngredientsFrag) ingredientsFrag = (IngredientsFrag) fragment;

    }


    @Override
    public void next(View view) {
        if(stepFrag != null) stepFrag.loadNext();
    }

    @Override
    public void previous(View view) {
        if(stepFrag != null) stepFrag.loadPrevious();
    }

}
