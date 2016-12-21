package com.jafir.mybookexplore;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;


/**
 * Created by jafir on 16/12/20.
 */

public class MyViewpager extends ViewPager {

    private int x, y;
    private int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    public MyViewpager(Context context) {
        super(context);
    }

    public MyViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 重写 把protected改成public
     *
     * @param child
     */
    @Override
    public void detachViewFromParent(View child) {
        super.detachViewFromParent(child);
    }


    @Override
    public void attachViewToParent(View child, int index, ViewGroup.LayoutParams params) {
        super.attachViewToParent(child, index, params);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) ev.getX();
                y = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int x1 = (int) ev.getX();
                int y1 = (int) ev.getY();
                int dx = x1 - x;
                int dy = y1 - y;
                if (Math.abs(dx) < Math.abs(dy) && Math.abs(dy) > touchSlop + 60) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
