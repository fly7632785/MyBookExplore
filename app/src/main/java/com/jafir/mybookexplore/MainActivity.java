package com.jafir.mybookexplore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jafir.mybookexplore.widget.MyRoundImageView;
import com.jafir.mybookexplore.widget.Xfermodes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toScreen(v);
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearch(v);
                overridePendingTransition(R.anim.enter_up, R.anim.out_up);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnimatorActivity.class));
            }
        });
        final MyRoundImageView myRoundImageView = (MyRoundImageView) findViewById(R.id.myavatar);
        myRoundImageView.setOutBorder(10, getResources().getColor(R.color.colorPrimary));
        myRoundImageView.setInsideColor(getResources().getColor(R.color.colorAccent));
        myRoundImageView.setInsideThickness(0);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRoundImageView.setOutBorder(15, getResources().getColor(R.color.colorAccent));
                myRoundImageView.setInsideColor(getResources().getColor(R.color.colorPrimary));
                myRoundImageView.setInsideThickness(10);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, Xfermodes.class));
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, TestViewMove.class));
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, TestMyViewpagerActivity.class));
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, TestMyViewpagerActivity1.class));
            }
        });



    }

    private void toSearch(View v) {
        startActivity(new Intent(this, SearchActivity.class));
    }

    private void toScreen(View view) {
        startActivity(new Intent(this, PreferenceScreenActivity.class));
    }



}
