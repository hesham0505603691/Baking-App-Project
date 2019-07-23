package com.hesham.baking;

import java.util.List;
import timber.log.Timber;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.content.Context;
import android.view.LayoutInflater;

public class IngredientsAdapt extends RecyclerView.Adapter<IngredientsAdapt.IngredientViewHolder>{

    @BindView(R.id.ingredient_name) TextView tv_ingredient_name;
    @BindView(R.id.ingredient_quantity) TextView tv_ingredient_quantity;
    @BindView(R.id.ingredient_measure) TextView tv_ingredient_measure;

    private List<Baking.ingredients> mListIngredients;

    public IngredientsAdapt(List<Baking.ingredients> ingredientItems) {
        mListIngredients = ingredientItems;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredient_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        IngredientViewHolder viewHolder = new IngredientViewHolder(view);
        ButterKnife.bind(this, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Timber.d("#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (mListIngredients == null) return 0;

        return mListIngredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {


        public IngredientViewHolder(View itemView) {
            super(itemView);


        }

        void bind(int index) {
            tv_ingredient_name.setText(mListIngredients.get(index).ingredient);
            tv_ingredient_measure.setText(mListIngredients.get(index).measure);
            tv_ingredient_quantity.setText(mListIngredients.get(index).quantity + "");

        }


    }

    public void setData(List<Baking.ingredients> ingredients) {
        mListIngredients = ingredients;
        notifyDataSetChanged();
    }

}
