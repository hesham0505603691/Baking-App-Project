package com.hesham.baking;


import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

class ListViewsRemote implements RemoteViewsService.RemoteViewsFactory  {
    Context mContext;
    Cursor mCursor;
    long ID;

    public ListViewsRemote(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        ID = intent.getLongExtra(BakingWidget.RECIPE_ID, BakingWidget.FIRST);
    }

    @Override
    public void onCreate() {
    }


    @Override
    public void onDataSetChanged() {
        Log.d("DataSetChanged","The id from intend is " + ID);

        Uri ingredient_url = RecipeProvider.Ingredients.CONTENT_URI.buildUpon().appendPath(ID + "").build();
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                ingredient_url,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDestroy() {
        if (mCursor == null) return;
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);
        int recipeIDIndex = mCursor.getColumnIndex(IngredientsContract.COL_RECIPE_ID);
        int ingredientNameIndex = mCursor.getColumnIndex(IngredientsContract.COL_INGREDIENT);
        int ingredientMeasureIndex = mCursor.getColumnIndex(IngredientsContract.COL_MEASURE);
        int ingredientQuantityIndex = mCursor.getColumnIndex(IngredientsContract.COL_QUANTITY);


        int recipeID = mCursor.getInt(recipeIDIndex);
        String ingredientName = mCursor.getString(ingredientNameIndex);
        String ingredientMeasure = mCursor.getString(ingredientMeasureIndex);
        String ingredientQuantity = mCursor.getString(ingredientQuantityIndex);


        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.bake_ingredient_widget_item);

        views.setTextViewText(R.id.widget_ingredient_name, String.valueOf(ingredientName));

        views.setTextViewText(R.id.widget_ingredient_measure_quantity, String.valueOf(ingredientMeasure + " " + ingredientQuantity));

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
