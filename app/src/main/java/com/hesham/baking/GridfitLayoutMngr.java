package com.hesham.baking;


import android.util.TypedValue;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class GridfitLayoutMngr extends GridLayoutManager {

    private static final int WIDTH = 900;
    private int mColWidth;
    private boolean mColWidthDif = true;

    public GridfitLayoutMngr(Context context, int colWidth) {
        super(context, 1);
        setColumnWidth(checkedColumnWidth(context, colWidth));
    }

    public GridfitLayoutMngr(Context context, int colWidth, int orient, boolean reverseLayout)
    {
        super(context, 1, orient, reverseLayout);
        setColumnWidth(checkedColumnWidth(context, colWidth));
    }


    private int checkedColumnWidth(Context context, int colWidth)
    {
        if (colWidth <= 0)
        {
            colWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH, context.getResources()
                    .getDisplayMetrics());
        }
        return colWidth;
    }


    private void setColumnWidth(int difColWidth) {
        if (difColWidth > 0 && difColWidth != mColWidth) {
            mColWidth = difColWidth;
            mColWidthDif = true;
        }
    }


    @Override public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (mColWidthDif && mColWidth > 0) {
            int totalSpace;
            if (getOrientation() == VERTICAL) {
                totalSpace = getWidth() - getPaddingRight() - getPaddingLeft();
            } else {
                totalSpace = getHeight() - getPaddingTop() - getPaddingBottom();
            }
            int spanCount = Math.max(1, totalSpace / mColWidth);
            setSpanCount(spanCount);
            mColWidthDif = false;
        }
        super.onLayoutChildren(recycler, state);
    }
}
