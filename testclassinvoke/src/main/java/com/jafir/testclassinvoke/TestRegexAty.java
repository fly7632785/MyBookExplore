package com.jafir.testclassinvoke;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jafir on 17/1/13.
 */

public class TestRegexAty extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_regex);

        init();

    }
    private String s1="123123..43#4@@akjhfjkdas))(--=.....\\][]ppp'';;l";
    private void init() {

        Log.e("debug","s1:"+s1);

        //匹配asd任意一个的字符 这里去掉了a das
        String ss1 = s1.replaceAll("[asd]","");
        Log.e("debug","ss1:"+ss1);

        //匹配除了asd之外的字符 这里去掉了a das其他的字符
        String ss2 = s1.replaceAll("[^asd]","");
        Log.e("debug","ss2:"+ss2);

        //只有replaceAll才有 匹配正则
        String ss3 = s1.replace("[^asd]","");
        Log.e("debug","ss3:"+ss3);

        //  \\d匹配数字    \\D匹配数字之外的
        String ss4 = s1.replaceAll("[^4,\\D]","");
        Log.e("debug","ss4:"+ss4);

        Pattern pattern = Pattern.compile("[,123 <p> </p>|]+");
        String[] strs = pattern.split("Java </p>Hello <p>World 123 Java,Hello,,World|Sun");
        for (int i=0;i<strs.length;i++) {
            Log.e("debug","strs:"+strs[i]);
        }

        Pattern pattern1 = Pattern.compile("正则表达式");
        Matcher matcher = pattern1.matcher("正则表达式 Hello World,正则表达式 Hello World");
//替换第一个符合正则的数据
        System.out.println(matcher.replaceFirst("Java"));

        Pattern pattern2 = Pattern.compile("正则表达式");
        Matcher matcher1 = pattern2.matcher("正则表达式 Hello World,正则表达式 Hello World");
//替换第一个符合正则的数据
        System.out.println(matcher1.replaceAll("Java"));



//去掉网页标签
        Pattern pattern22 = Pattern.compile("<.+?>", Pattern.DOTALL);
        Matcher matcher22 = pattern22.matcher("<a href=\"index.html\">主页</a>");
        String string = matcher22.replaceAll("");
        System.out.println(string);

       System.out.println( Pattern.matches("正则表达式","正则表达式 Hello World,正则表达式 Hello World"));
    }
}
