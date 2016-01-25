package cn.alien95.homework.app;

import android.app.Application;

import alien95.cn.http.request.HttpRequest;
import alien95.cn.util.SqlHelper;
import cn.alien95.homework.BuildConfig;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            alien95.cn.util.Utils.setDebug(true, "HomeWork");
            HttpRequest.setDebug(true, "NetWork");
        }

        SqlHelper.init(this, "HomeWork");  //初始化数据库
        SqlHelper.getInstance().addTable(new SqlHelper.TableName("ToDo"),);
    }
}
