package com.jafir.mybookexplore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jafir on 16/12/12.
 * 仿锤子阅读的 上移搜索
 */

public class SearchActivity extends AppCompatActivity {


    private TranslationSearchView searchView;
    private ListView searchListVIew;
    private ListView ListVIew;
    private ArrayAdapter mAdapter;
    private ArrayAdapter mSearchAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_search);

        init();
    }

    private void init() {
        //searchListview
        ListVIew = (ListView) findViewById(R.id.listview);
        //layout animation
        LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.in));
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        ListVIew.setLayoutAnimation(controller);

        searchListVIew = new ListView(this);
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                new String[]{"1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2"});
        mSearchAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                new String[]{"5", "5", "5", "2", "1", "2", "1", "2", "1", "2", "1", "2"});
        ListVIew.setAdapter(mAdapter);
        searchView = (TranslationSearchView) findViewById(R.id.searchview);
        searchView.setView(searchListVIew, mAdapter);
        searchView.setOpenListener(new TranslationSearchView.OpenListener() {
            @Override
            public void onOpenChanged(boolean isOpen) {

            }
        });

        searchView.setSearchDataLoadListener(new TranslationSearchView.SearchDataLoadListener() {
            @Override
            public void onPreDataLoad() {

            }

            @Override
            public void onLoad(String condition) {
                //请求 condition的数据接口
                searchListVIew.setAdapter(mSearchAdapter);
                List list = new ArrayList<>();
                list.add("1");
                searchView.showData(list);
            }

            @Override
            public void onDataLoaded() {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter,R.anim.out);
    }
}
