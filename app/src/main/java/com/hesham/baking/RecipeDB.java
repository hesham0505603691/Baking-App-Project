package com.hesham.baking;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.OnConfigure;
import net.simonvt.schematic.annotation.Table;

@Database(version = RecipeDB.VERSION, packageName = "com.hesham.baking.Data.provider")
public class RecipeDB {

    private RecipeDB(){}

    public static final int VERSION = 1;

    @Table(RecipeContract.class)
    public static final String RECIPES = "recipes";

    @Table(IngredientsContract.class)
    public static final String INGREDIENTS = "ingredients";

    @Table(StepsContract.class)
    public static final String STEPS = "steps";

    @OnCreate
    public static void onCreate(Context context, SQLiteDatabase db) {
    }

    @OnUpgrade
    public static void onUpgrade(Context context, SQLiteDatabase db, int oldVersion,
                                 int newVersion) {
    }

    @OnConfigure
    public static void onConfigure(SQLiteDatabase db) {
    }


}
