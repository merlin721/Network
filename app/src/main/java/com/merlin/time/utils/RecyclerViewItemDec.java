package com.merlin.time.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author zhouyang
 * @date 2019/1/13
 * @desc
 */
public class RecyclerViewItemDec extends RecyclerView.ItemDecoration {
    private int dieverHeight;
    public RecyclerViewItemDec(int dieverHeight){
        this.dieverHeight = dieverHeight;
    }
    @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
        RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildLayoutPosition(view);
        if (position == 0){
            outRect.top = dieverHeight;
        }

        outRect.bottom = dieverHeight;
    }
}
