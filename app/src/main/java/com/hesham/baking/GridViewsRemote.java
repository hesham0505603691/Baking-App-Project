package com.hesham.baking;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

class GridViewsRemote implements RemoteViewsService.RemoteViewsFactory  {

    Context mContext;
    Cursor mCursor;

    public GridViewsRemote(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

       }

    @Override
    public void onDataSetChanged() {
        Uri recipe_url = RecipeProvider.Recipes.CONTENT_URI;
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                recipe_url,
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


    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);
        int recipeIDIndex = mCursor.getColumnIndex(RecipeContract.COL_RECIPE_ID);
        int recipeNameIndex = mCursor.getColumnIndex(RecipeContract.COL_NAME);
        int recipeID = mCursor.getInt(recipeIDIndex);
        String recipeName = mCursor.getString(recipeNameIndex);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.bake_recipe_widget_item);
        int imgRes = 0;
        if(recipeID == 1) imgRes = R.drawable.nutella;
        else if(recipeID == 2) imgRes = R.drawable.brownies;
        else if(recipeID == 3) imgRes = R.drawable.yellowcake;
        else if(recipeID == 4) imgRes = R.drawable.cheesecake;
        else imgRes = R.drawable.cheesecake;

        views.setImageViewResource(R.id.recipe_widget_img, imgRes);
        views.setTextViewText(R.id.recipe_widget_name, String.valueOf(recipeName));
        Bundle extras = new Bundle();
        extras.putLong(BakingWidget.RECIPE_ID, recipeID);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.recipe_widget_img, fillInIntent);

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
