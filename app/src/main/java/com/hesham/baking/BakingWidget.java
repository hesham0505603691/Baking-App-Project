package com.hesham.baking;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class BakingWidget extends AppWidgetProvider {

    private static final String OPENING_LIST = "com.hesham.baking.widget.action.open_list";
    private static final String OPENING_GRID = "com.hesham.baking.widget.action.open_grid";
    public static final String RECIPE_ID = "com.hesham.baking.widget.extra.RECIPE_ID";
    public static final long FIRST = 1;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,int appWidgetId)
    {
        appWidgetManager = AppWidgetManager.getInstance(context);

        RemoteViews rv;
        rv = getRecipeGridRemoteView(context, appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
          updateAppWidget(context, appWidgetManager, appWidgetId);
       }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }


    /**
     * @param context The context
     * @param appWidgetId of widget we want to update
     * @return The RemoteViews for the GridView mode widget
     */
    public static RemoteViews getRecipeGridRemoteView(Context context, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bake_recipes_widget);

        Intent intent = new Intent(context, GridWidget.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        Intent showListIntent = new Intent(context, BakingWidget.class);
        showListIntent.setAction(OPENING_LIST);
        showListIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,appWidgetId,showListIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, pendingIntent);

        Intent homeIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntentHome = PendingIntent.getActivity(context, appWidgetId, homeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.home_widget_btn, pendingIntentHome);

        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        return views;
    }

    /**
     * @param context The context
     * @param id of recipe to open
     * @param appWidgetId of widget we want to update
     * @return The RemoteViews for the ListView mode widget
     */
    public static RemoteViews getRecipeListRemoteView(Context context,long id,int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bake__ingredients_widget);

        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putExtra(RECIPE_ID,id);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.widget_listview, intent);
        views.setEmptyView(R.id.widget_listview, R.id.empty_view);

        Intent showGridIntent = new Intent(context, BakingWidget.class);
        showGridIntent.setAction(OPENING_GRID);
        showGridIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, showGridIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.back_widget_btn, pendingIntent);

        return views;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);
        if (intent.getAction().equals(OPENING_LIST))
        {
            long id = intent.getLongExtra(RECIPE_ID, FIRST);
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,0);
            Log.d("Recieved","The id from intend is " + id);
            RemoteViews rv;

             rv = getRecipeListRemoteView(context,id, appWidgetId);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidgetId,rv);
        }
        else if (intent.getAction().equals(OPENING_GRID)){

            RemoteViews rv;
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,0);
            rv = getRecipeGridRemoteView(context,appWidgetId);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidgetId,rv);
        }
    }
}

