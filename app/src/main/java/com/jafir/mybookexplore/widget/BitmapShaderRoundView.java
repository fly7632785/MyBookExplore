package com.jafir.mybookexplore.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

import com.jafir.mybookexplore.R;

/**
 * Created by jafir on 16/12/19.
 * 这种是以 shader的方式
 * bitmapShader 处理SHapeDrawable
 * 设置一个原型shape
 * 然后传入原来的bitmap（处理缩放之后的图片）
 * 显示出来
 *
 *
 */

public class BitmapShaderRoundView extends View {
    private static final int DEFAULT_SIZE = 150;
    private BitmapShader bitmapShader = null;
    private Bitmap bitmap = null;
    private ShapeDrawable shapeDrawable = null;
    private int bitmapWidth = 0;
    private int bitmapHeight = 0;

    public BitmapShaderRoundView(Context context) {
        super(context);
    }

    public BitmapShaderRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BitmapShaderRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        //得到图像
        bitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.logo)).getBitmap();
        Bitmap bb = Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, true);
        //构造渲染器BitmapShader
        bitmapShader = new BitmapShader(bb, Shader.TileMode.MIRROR, Shader.TileMode.REPEAT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        bitmapWidth = measureSize(widthMeasureSpec);
        bitmapHeight = measureSize(heightMeasureSpec);
        setMeasuredDimension(bitmapWidth, bitmapHeight);
    }

    private int measureSize(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.AT_MOST) {
            result = DEFAULT_SIZE;
        } else {
            result = specSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bitmap == null) {
            //第一次初始化
            init();
        }
        //将图片裁剪为椭圆形
        //构建ShapeDrawable对象并定义形状为椭圆
        shapeDrawable = new ShapeDrawable(new OvalShape());
        //得到画笔并设置渲染器
        shapeDrawable.getPaint().setShader(bitmapShader);
        //设置显示区域
        shapeDrawable.setBounds(0, 0, bitmapWidth, bitmapHeight);
        //绘制shapeDrawable
        shapeDrawable.draw(canvas);
    }
}
