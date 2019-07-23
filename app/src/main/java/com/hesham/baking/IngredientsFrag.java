package com.hesham.baking;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;




public class IngredientsFrag extends Fragment {
    @BindView(R.id.rv_ingredients)
    RecyclerView rv_mBakingIngredient;

    public static final String SAVED_INGREDIENTS = "bake_ingredient";


    private IngredientsAdapt ingredientsAdapt;
    private List<Baking.ingredients> mIngredientList;
    public boolean recycleState = false;

    public IngredientsFrag(){}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            mIngredientList = savedInstanceState.getParcelableArrayList(SAVED_INGREDIENTS);
            ingredientsAdapt = new IngredientsAdapt(mIngredientList);
        }


        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        ButterKnife.bind(this, rootView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_mBakingIngredient.setLayoutManager(layoutManager);
        rv_mBakingIngredient.setFocusable(recycleState);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_mBakingIngredient.getContext(),
                layoutManager.getOrientation());
        rv_mBakingIngredient.addItemDecoration(dividerItemDecoration);

        rv_mBakingIngredient.setHasFixedSize(true);
        rv_mBakingIngredient.setAdapter(ingredientsAdapt);



        return rootView;
    }

    public void setData(List<Baking.ingredients> ingredients) {
        mIngredientList = ingredients;
        ingredientsAdapt = new IngredientsAdapt(mIngredientList);
    }

    public void setFocus() {
       recycleState = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SAVED_INGREDIENTS, (ArrayList) mIngredientList);
        super.onSaveInstanceState(outState);
    }

}
