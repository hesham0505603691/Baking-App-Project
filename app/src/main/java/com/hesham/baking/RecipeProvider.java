package com.hesham.baking;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import net.simonvt.schematic.annotation.NotifyDelete;
import net.simonvt.schematic.annotation.NotifyInsert;
import net.simonvt.schematic.annotation.NotifyUpdate;
import net.simonvt.schematic.annotation.TableEndpoint;
import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.NotifyBulkInsert;

@ContentProvider(
        authority = RecipeProvider.AUTHORITY,
        database = RecipeDB.class,
        packageName = "com.hesham.baking.Data.provider")
public final class RecipeProvider {

   private RecipeProvider() {
    }


    public static final String AUTHORITY = "com.hesham.baking.Data.provider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    interface Path {
        String Recipes = "recipes";
        String Ingredients = "ingredients";
        String Steps = "steps";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = RecipeDB.RECIPES)
    public static class Recipes {

        @ContentUri(
                path = Path.Recipes,
                type = "vnd.android.cursor.dir/recipes",
                defaultSort = RecipeContract.COL_RECIPE_ID + " DESC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/recipes");

        @InexactContentUri(
                path = Path.Recipes + "/#",
                name = "Recipe_ID",
                type = "vnd.android.cursor.item/recipes",
                whereColumn = RecipeContract.COL_RECIPE_ID,
                pathSegment = 1)
        public static Uri recipe_withid (long id) {
            return buildUri(Path.Recipes, String.valueOf(id));
        }

        @NotifyInsert(paths = Path.Recipes) public static Uri[] onInsert(ContentValues values) {
            final long recipeId = values.getAsLong(RecipeContract.COL_RECIPE_ID);
            return new Uri[] {
                    recipe_withid(recipeId),
            };
        }

        @NotifyBulkInsert(paths = Path.Recipes)
        public static Uri[] onBulkInsert(Context context, Uri uri, ContentValues[] values, long[] ids) {
            return new Uri[] {
                    uri,
            };
        }

        @NotifyUpdate(paths = Path.Recipes + "/#") public static Uri[] onUpdate(Context context,
                                                                              Uri uri, String where, String[] whereArgs) {
            final long recipe_Id = Long.valueOf(uri.getPathSegments().get(1));

            return new Uri[] {
                    recipe_withid(recipe_Id),
            };
        }

        @NotifyDelete(paths = Path.Recipes + "/#") public static Uri[] onDelete(Context context,
                                                                              Uri uri) {
            final long recipe_Id = Long.valueOf(uri.getPathSegments().get(1));

            return new Uri[] {
                    recipe_withid(recipe_Id),
            };
        }

    }

    @TableEndpoint(table = RecipeDB.STEPS)
    public static class Steps {

        @ContentUri(
                path = Path.Steps,
                type = "vnd.android.cursor.dir/steps",
                defaultSort = StepsContract.COL_ID + " DESC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/steps");

        @InexactContentUri(
                path = Path.Steps + "/#",
                name = "Steps_ID",
                type = "vnd.android.cursor.item/Steps",
                whereColumn = StepsContract.COL_ID,
                pathSegment = 1)
        public static Uri step_withid (long id) {
            return buildUri(Path.Steps, String.valueOf(id));
        }

        @NotifyInsert(paths = Path.Steps) public static Uri[] onInsert(ContentValues values) {
            final long step_Id = values.getAsLong(StepsContract.COL_ID);
            return new Uri[] {
                    step_withid(step_Id),
            };
        }

        @NotifyBulkInsert(paths = Path.Steps)
        public static Uri[] onBulkInsert(Context context, Uri uri, ContentValues[] values, long[] ids) {
            return new Uri[] {
                    uri,
            };
        }

        @NotifyUpdate(paths = Path.Steps + "/#") public static Uri[] onUpdate(Context context,
                                                                              Uri uri, String where, String[] whereArgs) {
            final long step_Id = Long.valueOf(uri.getPathSegments().get(1));


            return new Uri[] {
                    step_withid(step_Id),
            };
        }

        @NotifyDelete(paths = Path.Steps + "/#") public static Uri[] onDelete(Context context,
                                                                              Uri uri) {
            final long step_Id = Long.valueOf(uri.getPathSegments().get(1));

            return new Uri[] {
                    step_withid(step_Id),
            };
        }

    }


    @TableEndpoint(table = RecipeDB.INGREDIENTS)
    public static class Ingredients {


        @ContentUri(
                path = Path.Ingredients,
                type = "vnd.android.cursor.dir/ingredients",
                defaultSort = IngredientsContract.COL_ID + " DESC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/ingredients");

        @InexactContentUri(
                path = Path.Ingredients + "/#",
                name = "Ingredient_ID",
                type = "vnd.android.cursor.item/ingredients",
                whereColumn = IngredientsContract.COL_RECIPE_ID,
                pathSegment = 1)
        public static Uri ingredient_withid (long id) {
            return buildUri(Path.Ingredients, String.valueOf(id));
        }

        @NotifyInsert(paths = Path.Ingredients) public static Uri[] onInsert(ContentValues values) {
            final long ingredientsId = values.getAsLong(IngredientsContract.COL_ID);
            return new Uri[] {
                    ingredient_withid(ingredientsId),
            };
        }

        @NotifyBulkInsert(paths = Path.Ingredients)
        public static Uri[] onBulkInsert(Context context, Uri uri, ContentValues[] values, long[] ids) {
            return new Uri[] {
                    uri,
            };
        }

        @NotifyUpdate(paths = Path.Ingredients + "/#") public static Uri[] onUpdate(Context context,
                                                                              Uri uri, String where, String[] whereArgs) {
            final long ingredients_Id = Long.valueOf(uri.getPathSegments().get(1));

            return new Uri[] {
                    ingredient_withid(ingredients_Id),
            };
        }

        @NotifyDelete(paths = Path.Ingredients + "/#") public static Uri[] onDelete(Context context,
                                                                              Uri uri) {
            final long ingredientsId = Long.valueOf(uri.getPathSegments().get(1));
            return new Uri[] {
                    ingredient_withid(ingredientsId),
            };
        }
    }
}
