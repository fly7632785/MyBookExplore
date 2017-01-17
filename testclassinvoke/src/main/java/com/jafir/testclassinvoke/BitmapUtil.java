package com.jafir.testclassinvoke;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jafir on 17/1/16.
 */

public class BitmapUtil {


    public static Bitmap compress(Resources res, int resId, int height, int width) {


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        //计算samplesize采样率
        int sampleSize = calcutaSampleSize(options, height, width);
        options.inSampleSize = sampleSize;

        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
        return bitmap;

    }


    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        //把所有的变量收集到一起，然后一次性把数据发送出去
        byte[] buffer = new byte[1024]; // 用数据装
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            outstream.write(buffer, 0, len);
        }
        outstream.close();

        return outstream.toByteArray();
    }


    public static Bitmap compress(InputStream inputStream, int height, int width) {


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        byte[] data = new byte[0];
        try {
            data = getBytes(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //这个方法有问题 会decode null
//      Bitmap bi =   BitmapFactory.decodeStream(inputStream, null, options);
//      BufferedInputStream bis = new BufferedInputStream(inputStream);
//      Bitmap bi =   BitmapFactory.decodeStream(bis, null, options);
        Bitmap bi = BitmapFactory.decodeByteArray(data, 0, data.length, options);

        //计算samplesize采样率
        int sampleSize = calcutaSampleSize(options, height, width);
        options.inSampleSize = sampleSize;

        options.inJustDecodeBounds = false;
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
//        Bitmap bitmap =   BitmapFactory.decodeStream(bis, null, options);
        /**
         * 只有这种可用 前面两种都decode为null
         */
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        return bitmap;

    }

    public static Bitmap compress(FileDescriptor fd, int height, int width) {


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        //计算samplesize采样率
        int sampleSize = calcutaSampleSize(options, height, width);
        options.inSampleSize = sampleSize;

        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd, null, options);
        return bitmap;

    }

    private static int calcutaSampleSize(BitmapFactory.Options options, int height, int width) {

        if (height == 0 || width == 0) {
            return 1;
        }

        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;


        Log.d("debug", "width:" + bitmapWidth + "  height:" + bitmapHeight);
        int sampleSize = 1;
        /**
         * 如果图片大小比指定大小大
         * 就先都/2
         * 此时samplesize为1
         */
        if (bitmapHeight > height || bitmapWidth > width) {
            final int halfWidth = bitmapWidth / 2;
            final int halfHeight = bitmapHeight / 2;

            /**
             * 如果都/2之后 再计算/samplesize是否还大于指定width
             * 如果宽高都还大于指定的宽高
             * 那么就再增加samplesize的除数，知道 有一个小于指定宽高或者两个都小于指定宽高
             *
             */
            while ((halfWidth / sampleSize >= width) && (halfHeight / sampleSize > height)) {
                sampleSize *= 2;
            }

        }
        return sampleSize;

    }


}
