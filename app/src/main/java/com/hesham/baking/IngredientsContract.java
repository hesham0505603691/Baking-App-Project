package com.hesham.baking;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;


public class IngredientsContract {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey(onConflict = ConflictResolutionType.ABORT)
    @AutoIncrement
    public static final String COL_ID = "_id";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String COL_RECIPE_ID = "recipe_id";

    @DataType(DataType.Type.REAL)
    @NotNull
    public static final String COL_QUANTITY = "quantity";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COL_MEASURE = "measure";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COL_INGREDIENT = "ingredient";

}
