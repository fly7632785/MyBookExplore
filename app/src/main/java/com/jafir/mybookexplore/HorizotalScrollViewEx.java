package com.jafir.mybookexplore;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by jafir on 16/12/6.
 */

public class HorizotalScrollViewEx extends ViewGroup {


    private VelocityTracker mVelocity;
    private Scroller mScroller;
    private int oldX;
    private int oldY;
    private int mChildeIndex;
    private int mChildWidth;
    private int oldInterceptX;
    private int oldInterceptY;


    public HorizotalScrollViewEx(Context context) {
        super(context);
        init(context);
    }


    public HorizotalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizotalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mVelocity = VelocityTracker.obtain();
        mScroller = new Scroller(context);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //1.测量子View
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        } else {
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                final View child = getChildAt(0);
                setMeasuredDimension(child.getMeasuredWidth() * getChildCount(), child.getMeasuredHeight());
            } else if (widthMode == MeasureSpec.AT_MOST) {
                final View child = getChildAt(0);
                setMeasuredDimension(child.getMeasuredWidth() * getChildCount(), heightSize);
            } else if (heightMode == MeasureSpec.AT_MOST) {
                final View child = getChildAt(0);
                setMeasuredDimension(child.getMeasuredWidth() * getChildCount(), child.getMeasuredHeight());
            }
        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            /**
             * 最好这里来取 child 宽
             */
            mChildWidth = view.getMeasuredWidth();
            if (view.getVisibility() != GONE) {
                view.layout(left, t, left + view.getMeasuredWidth(), b);
                left += view.getMeasuredWidth();
            }
        }

    }


    /**
     * 解决 左右和上下的冲突
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercetp = false;
        /**
         * 这里要严肃注意 错误！
         */
//        int x = (int) getX();
//        int y = (int) getY();
        int x = (int) ev.getX();
        int y = (int) ev.getY();


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercetp = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercetp = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - oldInterceptX;
                Log.d("debug", "dx:" + dx);
                int dy = y - oldInterceptY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    intercetp = true;
                } else {
                    intercetp = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                intercetp = false;
                break;
        }

        Log.d("debug", "interceopt:" + intercetp);

        oldInterceptX = x;
        oldInterceptY = y;

        oldX = x;
        oldY = y;

        /**
         * 这里要返回intercept
         */
        return intercetp;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocity.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dX = x - oldX;
                int dy = y - oldY;

                /**
                 * 注意方向 是相反的
                 */
                scrollBy(-dX, 0);
                break;
            case MotionEvent.ACTION_UP:
                //获取移动的x距离
                int scrollX = getScrollX();
                //计算滑动速度
                /**
                 * 根据速度是否大于50来判断 是否要向前滑动完成还是回退收缩
                 */
                mVelocity.computeCurrentVelocity(1000);
                float xVelocity = mVelocity.getXVelocity();

                if (xVelocity > 50) {
                    mChildeIndex = xVelocity > 0 ? mChildeIndex - 1 : mChildeIndex + 1;
                } else {
                    mChildeIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }

                /**
                 * 这句话很关键，
                 * 首先 index 和 总共的index取小  因为不能超过最大的size-1
                 * 然后取大  必须要大于0
                 */
                mChildeIndex = Math.max(0, Math.min(mChildeIndex, getChildCount() - 1));
                int dx = mChildeIndex * mChildWidth - scrollX;
                smoothScrollBy(dx, 0);
                mVelocity.clear();

                break;
        }
        oldX = (int) event.getX();
        oldY = (int) event.getY();

        /**
         * 这里要设置为true 表示消费事件
         */
        return true;
    }

    private void smoothScrollBy(int dx, int i) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        /**
         * 注意这里要刷新 重绘
         * * 这个方法是在 主线程调用的
         */
        invalidate();
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            /**
             * 这里要刷新
             * 这个方法是在 非主线程调用的
             */
            postInvalidate();
//            invalidate();
        }
    }
}
