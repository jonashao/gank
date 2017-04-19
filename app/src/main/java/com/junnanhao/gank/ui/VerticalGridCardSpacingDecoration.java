package com.junnanhao.gank.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

/**
 * Modifies item spacing in a recycler view so that items are equally spaced no matter where they
 * are on the grid.
 */
public class VerticalGridCardSpacingDecoration extends ItemDecoration {
    private static final int OUTER_PADDING_DP = 8;
    private static final int INNER_PADDING_DP = 6;
    private int outerPadding = -1;
    private int innerPadding = -1;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        if (outerPadding == -1 || innerPadding == -1) {
            DisplayMetrics m = view.getResources().getDisplayMetrics();
            outerPadding = (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP, OUTER_PADDING_DP, m);
            innerPadding = (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP, INNER_PADDING_DP, m);
        }

        // Zero everything out for the common case
        outRect.setEmpty();
        outRect.bottom = innerPadding;
    }
}
