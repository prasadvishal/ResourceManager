package com.mindfiresolutions.resourcemanager.resource;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;

/**
 * Created by Shivangi Singh on 3/15/2017.
 * modified on 3/23/2017
 *
 */

 public class RecyclerItemDecorator extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private final String mTAG = "RecyclerItemDecorator";

     public RecyclerItemDecorator(Context context) {
         mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        LoggerUtility.log(mTAG,"This is RecyclerItemDecorator constructor");
    }
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        LoggerUtility.log(mTAG,"On draw over function");
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            //after getting bounds of left right top and bottom, draw the line
            mDivider.draw(c);
        }
    }
}
