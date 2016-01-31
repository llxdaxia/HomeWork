package cn.alien95.homework.app;

import android.app.Application;

import alien95.cn.http.request.Http;
import alien95.cn.util.SqlHelper;
import cn.alien95.homework.BuildConfig;
import cn.alien95.homework.config.API;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        alien95.cn.util.Utils.initialize(this);
        Http.initialize(this);
        if (BuildConfig.DEBUG) {
            alien95.cn.util.Utils.setDebug(true, "HomeWork");
            Http.setDebug(true, "NetWork");
        }

        SqlHelper.init(this, "HomeWork");  //初始化数据库
        SqlHelper.getInstance().addTable(API.TODO_TABLE_NAME, API.TODO_TABLE_SQL);  //添加数据库表
    }
}
