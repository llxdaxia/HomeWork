package cn.alien95.homework.moudel.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import cn.alien95.homework.R;
import cn.alien95.homework.app.BaseActivity;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class WeatherActivity extends BaseActivity{

    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
