package com.jafir.mybookexplore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jafir.mybookexplore.widget.MySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jafir on 16/12/20.
 */

public class TestMyViewpagerActivity1 extends AppCompatActivity {

    ViewPager mViewpager;
    List<SwipeRefreshLayout> webViews = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_viewpager1);

        init();
    }

    private void init() {

        for (int i = 0; i < 10; i++) {
            MySwipeRefreshLayout swipeRefreshLayout = new MySwipeRefreshLayout(this);
            WebView webView = new WebView(this);
            webView.getSettings().setJavaScriptEnabled(true);
//启动缓存
            webView.getSettings().setAppCacheEnabled(true);
            //设置缓存模式
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            //加载网页
//            webView.loadUrl("http://github.com/fly7632785");
            webView.loadUrl("https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=index&fr=&hs=2&xthttps=11111&sf=1&fmq=&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=美女&oq=美女&rsp=-1");webView.loadUrl("http://image.baidu.com/search/wiseala?tn=wiseala&iswiseala=1&ie=utf8&wiseps=1&word=%E7%BE%8E%E6%99%AF%E5%9B%BE");
            swipeRefreshLayout.addView(webView);
            webViews.add(swipeRefreshLayout);

        }


        mViewpager = (ViewPager) findViewById(R.id.viewpager1);
        mViewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(webViews.get(position));
                return webViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

        });

    }
}
