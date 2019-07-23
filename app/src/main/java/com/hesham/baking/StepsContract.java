package com.hesham.baking;

import net.simonvt.schematic.annotation.DataType.Type;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;

public class StepsContract {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey(onConflict = ConflictResolutionType.ABORT)
    @AutoIncrement
    public static final String COL_ID = "_id";

    @DataType(Type.INTEGER)
    @NotNull
    public static final String COL_RECIPE_ID = "recipe_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COL_SHORT_DESCRIPTION = "short_description";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COL_VIDEO_URL = "video_url";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COL_THUMBNAIL_URL = "thumbnail_url";

}
