package cn.alien95.homework.moudel.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import alien95.cn.util.TimeTransform;
import alien95.cn.util.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.alien95.homework.R;
import cn.alien95.homework.app.BaseActivity;
import cn.alien95.homework.config.API;
import cn.alien95.homework.model.WeatherServiceAPIModel;
import cn.alien95.homework.model.bean.WeatherData;
import rx.Observer;

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
    ImageView bgImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        ButterKnife.bind(this);
        setToolbarIsBack(true);

        Glide.with(this)
                .load(API.WEATHER_BG_IMG)
                .centerCrop()
                .into(bgImage);

        WeatherServiceAPIModel.getInstance().getWeatherFromCityName("重庆", new Observer<WeatherData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.Toast("网络错误");
            }

            @Override
            public void onNext(WeatherData weatherData) {
                if (weatherData.resultcode == 200) {
                    weather.setText(weatherData.result.today.weather);
                    rangeTemperature.setText(weatherData.result.today.temperature);
                    currentTemp.setText(weatherData.result.sk.temp + "℃");
                    time.setText(TimeTransform.getInstance().transformFormat(System.currentTimeMillis(), "hh:ss"));
                    location.setText("重庆");
                }
            }
        });

    }
}
