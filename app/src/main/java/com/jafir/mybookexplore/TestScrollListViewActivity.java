package com.jafir.mybookexplore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jafir on 16/12/21.
 */

public class TestScrollListViewActivity extends AppCompatActivity {


    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_scroll_listview);

        init();
    }

    private void init() {

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("" + i);
        }
        listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data);
        listView.setAdapter(adapter);


    }
}
