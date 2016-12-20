package com.jafir.mybookexplore.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.jafir.mybookexplore.R;

/**
 * Created by jafir on 16/12/14
 * 自定义圆角头像
 * <p>
 * 步骤：
 * 1、首先考虑有哪些 可调变量 比如 内边框 外边框 的颜色 厚度 等
 * 2、初始化 一些东西 这里把drawable转化为我们更为方便操作的 bitmap
 * 3、先根据是否有内外边框 来绘制边框（默认没有外边框 但是有内边框）注意要是空心的
 * 4、然后就是绘制图像 大小为 i4mageView减去了边框之后的radius
 * 5、绘制图像的时候，要判断图片的长宽，取正方形，如果大了小了还要缩放
 * 6、然后创建新的画布，最后 画两次
 * 7、一次是圆圈 就是根据radius来画，一次是我们图像，
 * 8、采用SRC_IN的XFermode 来相切裁剪 保留上层（由于我们先画圆圈）
 * 9、这样我们就裁剪好了
 */

public class MyRoundImageView extends ImageView {


    private int DEFAULT_COLOR = 0xffffffff;
    //内圈厚度
    private int insideThickness = 2;
    //外圈厚度
    private int outsideThickness = 0;
    //内圈颜色 默认使用内圈
    private int insideColor = DEFAULT_COLOR;
    //外圈颜色 -1表示不使用
    private int outsideColor = 0;
    private Bitmap currentBitmap;

    public MyRoundImageView(Context context) {
        super(context);
    }

    public MyRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyRoundImageView);
        if (array != null) {
            insideColor = array.getColor(R.styleable.MyRoundImageView_insideColor, DEFAULT_COLOR);
            outsideColor = array.getColor(R.styleable.MyRoundImageView_outsideColor, 0);
            insideThickness = array.getDimensionPixelSize(R.styleable.MyRoundImageView_insideThickness, 2);
            outsideThickness = array.getDimensionPixelSize(R.styleable.MyRoundImageView_outsideThickness, 0);
            array.recycle();
        }
    }

    public MyRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyRoundImageView, defStyleAttr, 0);
        if (array != null) {
            insideColor = array.getColor(R.styleable.MyRoundImageView_insideColor, DEFAULT_COLOR);
            outsideColor = array.getColor(R.styleable.MyRoundImageView_outsideColor, 0);
            insideThickness = array.getDimensionPixelSize(R.styleable.MyRoundImageView_insideThickness, 2);
            outsideThickness = array.getDimensionPixelSize(R.styleable.MyRoundImageView_outsideThickness, 0);
            array.recycle();
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 父类一定不能写
         */
//        super.onDraw(canvas);

        initCurrentBitmap();
        if (currentBitmap == null || getWidth() == 0 || getHeight() == 0) {
            return;
        }
        this.measure(0, 0);
        int width = getWidth();
        int height = getHeight();
        int radius = 0;

        //两个颜色都不为0 且厚度不为0 则画 两个圈
        if ((insideColor != 0 && insideThickness != 0) && (outsideColor != 0 && outsideThickness != 0)) {
            //计算内圆的radius
            /**
             * 半径为 首先取宽高的 最小   然后 除以2  - 边框的宽度
             */
            radius = (width > height ? height : width) / 2 - (insideThickness + outsideThickness);
            //画内圈 radius 是图像半径 + 厚度 / 2
            drawBorder(canvas, radius + insideThickness / 2, insideThickness, width, height, insideColor);
            //画外圈
            drawBorder(canvas, radius + insideThickness + outsideThickness / 2, outsideThickness, width, height, outsideColor);

        } else if (insideColor != 0 && insideThickness != 0) {
            //画内圆
            //计算内圆的radius
            radius = (width > height ? height : width) / 2 - insideThickness;
            //画内圈 radius 是图像半径 + 厚度 / 2
            drawBorder(canvas, radius + insideThickness / 2, insideThickness, width, height, insideColor);
        } else if (outsideColor != 0 && outsideThickness != 0) {
            //计算内圆的radius
            radius = (width > height ? height : width) / 2 - outsideThickness;
            //画外圈
            drawBorder(canvas, radius + insideThickness + outsideThickness / 2, outsideThickness, width, height, insideColor);
        } else {
            radius = (width > height ? height : width) / 2;
        }


        /**
         * 这里要加一个判断，当心：外圈+内圈 就已经大于等于 imageView的大小了 radius就小于=0 了
         *
         */
        if (radius > 0) {
            Bitmap bitmap = getCroppedBitmap(currentBitmap, radius);
            canvas.drawBitmap(bitmap, width / 2 - radius, height / 2 - radius, null);
        }


//        setLayerType(LAYER_TYPE_SOFTWARE,null);
        Log.d("debug","mine ondraw is?"+canvas.isHardwareAccelerated());

    }


    /**
     * 获取裁剪后的图片
     *
     * @param currentBitmap
     * @param radius
     * @return
     */
    private Bitmap getCroppedBitmap(Bitmap currentBitmap, int radius) {
        /**
         * 这里我们需要把可能为长方形的图像
         * 处理为正方形
         * 且为中间位置的
         *
         */
        int squareLength = 0;
        Bitmap squareBitmap;
        Bitmap scaleBitmap;
        int diameter = radius * 2;
        int x = 0, y = 0;
        int bitmapWidth, bitmapHeight;

        bitmapHeight = currentBitmap.getHeight();
        bitmapWidth = currentBitmap.getWidth();

        //如果长大于宽 取宽 算出 截取的位置
        if (bitmapHeight == bitmapWidth) {
            squareLength = bitmapHeight;
        } else if (bitmapHeight > bitmapWidth) {
            squareLength = bitmapWidth;
            y = (bitmapHeight - bitmapWidth) / 2;
        } else {
            x = (bitmapWidth - bitmapHeight) / 2;
        }

        squareBitmap = Bitmap.createBitmap(currentBitmap, x, y, squareLength, squareLength);


        /**
         * 放大
         */
        if (squareBitmap.getWidth() != diameter || squareBitmap.getHeight() != diameter) {
            scaleBitmap = Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);
        } else {
            scaleBitmap = squareBitmap;
        }

        /**
         * 输出 outBitmap
         * 1、创建一个 放大之后的scaleBitmap
         * 2、创建一个新的属于outBitmap的画布（要在上面画图像），new Rect 矩形
         * 3、画scale大小的圆
         * 4、叠合裁剪
         * 5、在canvas上面划出来
         *
         */
        Bitmap outBitmap = Bitmap.createBitmap(scaleBitmap.getWidth(), scaleBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        //剪切的矩形
        Rect rect = new Rect(0, 0, scaleBitmap.getWidth(), scaleBitmap.getHeight());
        //显示的矩形  如果这个比剪切的小 那么会压缩
        Rect rect1 = new Rect(0, 0, scaleBitmap.getWidth(), scaleBitmap.getHeight());
        paint.setAntiAlias(true);
        //对位图进行滤波处理，如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        /**
         *
         *
         * 第一种
         * 先画 circle
         * setXfermode Src_in
         * 再画circle
         *
         * 第二种 新开一个bitmap在bitamp里面画circle
         * 就可以实现
         * 先画 图片
         * setXfermode Dst_in
         * 再画 circle
         *
         */

        //第一种
        canvas.drawCircle(scaleBitmap.getWidth() / 2, scaleBitmap.getHeight() / 2, scaleBitmap.getWidth() / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(scaleBitmap, rect, rect1, paint);
//
//        Log.d("debug","mine is?"+canvas.isHardwareAccelerated());
        //第二种
//        canvas.drawBitmap(scaleBitmap, rect, rect1, paint);
//
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//
//        Bitmap bitmap = Bitmap.createBitmap(scaleBitmap.getWidth(), scaleBitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas1 = new Canvas(bitmap);
//        Paint p = new Paint();
//        canvas1.drawARGB(0, 0, 0, 0);
//        canvas1.drawCircle(scaleBitmap.getWidth() / 2, scaleBitmap.getHeight() / 2, scaleBitmap.getWidth() / 2, p);
//        canvas.drawBitmap(bitmap, rect, rect1, paint);
//

//


//        Bitmap b
//                = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
//        canvas.drawBitmap(b,rect, rect, paint);


        //置空 回收
        currentBitmap = null;
        squareBitmap = null;
        scaleBitmap = null;
        return outBitmap;

    }

    private void drawBorder(Canvas canvas, int radius, int borderThickness, int width, int height, int insideColor) {
        Paint paint = new Paint();
        paint.setColor(insideColor);
        paint.setAntiAlias(true);
        /**
         * 这里要画空心的
         */
        paint.setStyle(Paint.Style.STROKE);
        //对位图进行滤波处理，如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setStrokeWidth(borderThickness);
        canvas.drawCircle(width / 2, height / 2, radius, paint);
    }


    private void initCurrentBitmap() {
        Bitmap temp = null;
        Drawable drawable = getDrawable();
        if (drawable instanceof BitmapDrawable) {
            temp = ((BitmapDrawable) drawable).getBitmap();
        }
        if (temp != null) {
            currentBitmap = temp;
        }
    }


    /******************************************************************************
     * 供外部调用
     * 替换bitmap为我们使用
     */
    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        setCurrentBitmap(bm);
    }


    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        setCurrentBitmap(BitmapFactory.decodeResource(getResources(), resId));
    }


    public void setOutBorder(int thickness, int color) {
        outsideThickness = thickness;
        outsideColor = color;
        /**
         * 最好设置重画
         */
        invalidate();
    }

    public void setOutBorder(int thickness) {
        setOutBorder(thickness, DEFAULT_COLOR);
    }

    public void setDefaultOutBorder() {
        setOutBorder(2, DEFAULT_COLOR);
    }

    public void setInsideThickness(int insideThickness) {
        this.insideThickness = insideThickness;
        invalidate();
    }

    public void setInsideColor(int insideColor) {
        this.insideColor = insideColor;
        invalidate();
    }


}

