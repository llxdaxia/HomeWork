package cn.alien95.homework.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import alien95.cn.http.request.HttpCallBack;
import alien95.cn.http.request.HttpRequest;
import cn.alien95.homework.config.API;

/**
 * Created by linlongxin on 2016/1/18.
 */
public class WeatherModel extends Model {

    public static WeatherModel getInstance() {
        return (WeatherModel) getInstance(WeatherModel.class);
    }

    public void getWeatherCity(int cityId, HttpCallBack callBack) {
        String url = API.WEATHER_BASE_URL + "cityname=" + cityId + "&key=" + API.WEATHER_KEY;
        HttpRequest.getInstance().get(url, callBack);
    }

    public void getWeatherCity(String cityName, HttpCallBack callBack) {
        String cityUTF8 = null;
        try {
            cityUTF8 = URLEncoder.encode(cityName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = API.WEATHER_BASE_URL + "cityname=" + cityUTF8 + "&key=" + API.WEATHER_KEY;
        HttpRequest.getInstance().get(url, callBack);
    }
}
