package cn.alien95.homework.app;

import android.app.Application;

import cn.alien95.homework.BuildConfig;
import cn.alien95.homework.utils.SqlHelper;
import cn.alien95.homework.utils.Utils;
import cn.alien95.set.entrance.AlienSet;
import cn.alien95.set.http.request.HttpRequest;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //set 初始化
        AlienSet.init(this);
        AlienSet.setDebug(true, "HomeWork");
        if (BuildConfig.DEBUG) {
            HttpRequest.setDebug(true, "NetWork");
        }

        SqlHelper.init(this, "HomeWork");  //初始化数据库
        Utils.init(this);
        Utils.setDebugtag("HomeWork");


    }
}
