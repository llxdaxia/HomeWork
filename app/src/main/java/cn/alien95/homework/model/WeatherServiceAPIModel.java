package cn.alien95.homework.model;

import cn.alien95.homework.config.API;
import cn.alien95.homework.model.bean.WeatherData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linlongxin on 2016/1/18.
 */
public class WeatherServiceAPIModel extends Model {

    public static WeatherServiceAPIModel getInstance() {
        return (WeatherServiceAPIModel) getInstance(WeatherServiceAPIModel.class);
    }

    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API.WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //使用RxJava的时候要加上
            .client(okHttpClient)
            .build();

    private WeatherServiceAPI weatherServiceAPI = retrofit.create(WeatherServiceAPI.class);

    public void getWeatherFromCityName(String name, Observer<WeatherData> observer){
        Observable<WeatherData> observable = weatherServiceAPI.getWeatherFromName(2,name,API.WEATHER_KEY);
        observable.doOnNext(new Action1<WeatherData>() {
            @Override
            public void call(WeatherData weatherData) {
                //在执行完observer的doNext()方法后执行这里
            }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(observer);  //观察者和被观察者订阅
    }
}
