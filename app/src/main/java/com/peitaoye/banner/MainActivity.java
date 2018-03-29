package com.peitaoye.banner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<View> dataSet=new ArrayList<>();
        for(int i=0;i<3;i++){
            TextView textView=new TextView(this);
            textView.setText(i+"hello");
            textView.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setGravity(Gravity.CENTER);
            dataSet.add(textView);
        }
        BannerAdapter bannerAdapter=new BannerAdapter(dataSet);
        BannerPage bannerPager=(BannerPage) findViewById(R.id.banner);
        bannerPager.setAdapter(bannerAdapter);
//        BannerPage bannerPage=(BannerPage)findViewById(R.id.bannerPager);
//        bannerPage.setAdapter(bannerAdapter);
    }
}
