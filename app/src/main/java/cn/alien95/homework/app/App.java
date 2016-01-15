package cn.alien95.homework.app;

import android.app.Application;

import cn.alien95.homework.utils.SqlHelper;
import cn.alien95.homework.utils.Utils;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SqlHelper.init(this, "HomeWork");  //初始化数据库
        Utils.init(this);
    }
}
