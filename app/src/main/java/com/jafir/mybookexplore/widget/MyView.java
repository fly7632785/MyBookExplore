package com.jafir.mybookexplore.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jafir on 16/12/19.
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 方法1
         */
        Bitmap bm = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setShadowLayer(10,0,0, Color.CYAN);
        p.setColor(Color.BLUE);
        c.drawARGB(0,0,0,0);
        c.drawCircle(50,50,50,p);
        canvas.drawBitmap(bm,0,0,new Paint());


        /**
         * 方法2
         */
        Paint paint = new Paint();
        /**
         * 这里需要设置software  这个模式会生成bitmap
         */
        setLayerType( LAYER_TYPE_SOFTWARE , null);
        paint.setColor(Color.BLUE);
        paint.setShadowLayer(10,0,0, Color.CYAN);
        canvas.drawCircle(200,50,50,paint);



    }

}
