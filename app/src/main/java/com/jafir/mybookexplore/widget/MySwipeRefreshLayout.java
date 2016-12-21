package com.jafir.mybookexplore.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


/**
 * Created by jafir on 16/12/20.
 */

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    private int x, y;
    private int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    public MySwipeRefreshLayout(Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
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
                if (Math.abs(dx) < Math.abs(dy) && Math.abs(dy) > touchSlop+60) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
