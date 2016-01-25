package cn.alien95.homework.moudel.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.gson.Gson;

import alien95.cn.http.request.HttpCallBack;
import alien95.cn.http.view.HttpImageView;
import alien95.cn.util.TimeTransform;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.alien95.homework.R;
import cn.alien95.homework.app.BaseActivity;
import cn.alien95.homework.config.API;
import cn.alien95.homework.model.WeatherModel;
import cn.alien95.homework.model.bean.WeatherData;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class WeatherActivity extends BaseActivity {

    @Bind(R.id.location)
    TextView location;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.weather)
    TextView weather;
    @Bind(R.id.range_temperature)
    TextView rangeTemperature;
    @Bind(R.id.current_temp)
    TextView currentTemp;
    @Bind(R.id.bg_image)
    HttpImageView bgImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        ButterKnife.bind(this);
        setToolbarIsBack(true);

        bgImage.setImageUrl(API.WEATHER_BG_IMG);

        WeatherModel.getInstance().getWeatherCity("重庆", new HttpCallBack() {
            @Override
            public void success(String info) {
                Gson gson = new Gson();
                WeatherData data = gson.fromJson(info, WeatherData.class);
                if (data.resultcode == 200) {
                    weather.setText(data.result.today.weather);
                    rangeTemperature.setText(data.result.today.temperature);
                    currentTemp.setText(data.result.sk.temp + "℃");
                    time.setText(TimeTransform.getInstance().transformFormat(System.currentTimeMillis(), "hh:ss"));
                    location.setText("重庆");
                }

            }
        });

    }
}
