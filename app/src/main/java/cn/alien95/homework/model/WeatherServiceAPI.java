package cn.alien95.homework.model;

import cn.alien95.homework.model.bean.WeatherData;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by linlongxin on 2016/2/27.
 */
public interface WeatherServiceAPI {

    @GET("weather/index")
    Observable<WeatherData> getWeatherFromName(@Query("format") int format,@Query("cityname") String cityName,@Query("key") String key);

    @GET("weather/index")
    Observable<WeatherData> getWeatherFromId(@Query("format") int format,@Query("cityname") int cityId,@Query("key") String key);
}

