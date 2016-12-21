package com.jafir.mybookexplore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jafir on 16/12/21.
 */

public class TestScrollRecyclerViewActivity extends AppCompatActivity {


    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_scroll_recyclerview);

        init();
    }

    private void init() {

        final List<String> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            data.add("" + i);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView textView = new TextView(TestScrollRecyclerViewActivity.this);
                textView.setText("1");

                return new RecyclerView.ViewHolder(textView) {
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }


            @Override
            public int getItemCount() {
                return data.size();
            }
        });


    }
}
