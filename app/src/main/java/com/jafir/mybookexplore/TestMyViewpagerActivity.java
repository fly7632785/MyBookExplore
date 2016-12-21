package com.jafir.mybookexplore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jafir.mybookexplore.widget.MySwipeRefreshLayout;
import com.jafir.mybookexplore.widget.MyViewpager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jafir on 16/12/20.
 */

public class TestMyViewpagerActivity extends AppCompatActivity {
    MyViewpager mViewpager;
    List<SwipeRefreshLayout> webViews = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_viewpager);
        init();
    }
    private void init() {
        for (int i = 0; i < 10; i++) {
            MySwipeRefreshLayout mySwipeRefreshLayout = new MySwipeRefreshLayout(this);
            WebView webView = new WebView(this);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//            webView.loadUrl("http://github.com/fly7632785");
            webView.loadUrl("http://image.baidu.com/search/wiseala?tn=wiseala&iswiseala=1&ie=utf8&wiseps=1&word=%E7%BE%8E%E6%99%AF%E5%9B%BE");
            mySwipeRefreshLayout.addView(webView);
            webViews.add(mySwipeRefreshLayout);
        }
        mViewpager = (MyViewpager) findViewById(R.id.viewpager);
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
                if(!webViews.get(position).isLayoutRequested()){
                    ((MyViewpager)container).attachViewToParent(webViews.get(position),
                            -1,webViews.get(position).getLayoutParams());
                }else {
                    container.addView(webViews.get(position));
                }
                return webViews.get(position);
            }
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((MyViewpager) container).detachViewFromParent((View) object);
                container.invalidate();

            }

            @Override
            public void startUpdate(ViewGroup container) {
                int count = container.getChildCount();
                for (int i = 0; i < count; i++) {
                    container.getChildAt(i).forceLayout();
                }
                super.startUpdate(container);
            }
        });

    }
}
