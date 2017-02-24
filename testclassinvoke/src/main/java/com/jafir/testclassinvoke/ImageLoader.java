package com.jafir.testclassinvoke;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jafir on 17/1/16.
 */

public class ImageLoader {

    /**
     * 线程池
     */
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors() + 1;
    private static final int THREAD_MAX_COUNT = Runtime.getRuntime().availableProcessors() * 2 + 1;
    private static final Long KEEP_ALIVE = 10L;

    private static ImageLoader mInstance;

    private int memorySize = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private int cacheSize = memorySize / 8;
    private Context context;

    private LruCache mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
        }
    };

    private ImageLoader() {

    }

    private ImageLoader(Context context) {
        this.context = context;
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Result result = (Result) msg.obj;

            Bitmap bitmap = result.bitmap;
            ImageView imageview = result.imageview;
            String url = result.url;

            Log.e("debug","url:"+url);
            Log.e("debug","img:"+imageview.getTag(R.id.img));

            if (bitmap != null && bitmap != null && url.equals(imageview.getTag(R.id.img))) {
                imageview.setImageBitmap(bitmap);
            } else {
                Log.e("debug", "imageview或者bitmap为空或者不是同一个img");
            }
        }
    };

    public static synchronized ImageLoader getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new ImageLoader(context.getApplicationContext());
        }
        return mInstance;
    }


    public void bindBitmap(String url, final ImageView imageView) {
        if(imageView == null){
            return;
        }
        Log.d("debug","height  111:"+imageView.getHeight());
        Log.d("debug","width   111:"+imageView.getWidth());
        bindBitmap(url, imageView, imageView.getWidth(),imageView.getHeight());
    }


    public void bindBitmap(final String url, final ImageView imageView, final int width, final int height) {
        if(imageView == null){
            return;
        }
        imageView.setImageBitmap(null);
        /**
         * 绑定tag防止错乱
         */

        imageView.setTag(R.id.img,url);
        Log.e("debug","set url:"+url);

        /**
         * 从缓存拿 有就直接设置
         */
        Bitmap bitmap = getBitmapFromMemoryCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }

        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("所在的线程不是主线程");
        }

        hashName(url);

        THREAD_POOL_EXEUTOR.execute(new Runnable() {
            @Override
            public void run() {
                /**
                 * 不然就去网络请求
                 */
                InputStream inputstream = downLoadFromNet(url);
                Bitmap bitmap = null;
                if (inputstream != null) {
                    bitmap = BitmapUtil.compress(inputstream, height, width);
                    Log.d("debug", "width1:" + bitmap.getWidth() + "  height1:" + bitmap.getHeight());
                }
                /**
                 * 加入内存
                 */
                if (bitmap != null) {
                    addBitmapToMemoryCache(url, bitmap);
                }

                Result result = new Result(imageView, url, bitmap);
                Message msg = Message.obtain();
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });


    }


    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
//        如果缓存中没有就加入进去
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemoryCache(String key) {
        Bitmap bitmap = (Bitmap) mMemoryCache.get(key);
        return bitmap;
    }

    private InputStream downLoadFromNet(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            InputStream inputstream = connection.getInputStream();
            return inputstream;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String hashName(String url) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            return byteToHexString(digest.digest());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return String.valueOf(url.hashCode());
        }
    }

    private String byteToHexString(byte[] digest) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < digest.length; i++) {
            if (digest.length == 1) {
                stringBuilder.append('0');
            }
            stringBuilder.append(Integer.toHexString(0xFF & digest[i]));
        }

        Log.e("debug", "MD5:" + stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * 线程factory
     */
    private static ThreadFactory threadFactory = new ThreadFactory() {

        /**
         * 自增长的int数
         */
        private AtomicInteger atomicInteger = new AtomicInteger();

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "Thread#" + atomicInteger.getAndIncrement());
        }
    };


    /**
     * 线程池
     */
    private static ThreadPoolExecutor THREAD_POOL_EXEUTOR = new ThreadPoolExecutor(THREAD_COUNT, THREAD_MAX_COUNT, KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);


    private class Result {
        ImageView imageview;
        String url;
        Bitmap bitmap;

        public Result(ImageView imageview, String url, Bitmap bitmap) {
            this.imageview = imageview;
            this.url = url;
            this.bitmap = bitmap;
        }
    }

}
