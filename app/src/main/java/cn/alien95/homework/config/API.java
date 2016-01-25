package cn.alien95.homework.config;

/**
 * Created by linlongxin on 2016/1/18.
 */
public class API {

    public static final String TODO_TABLE_NAME = "ToDo";
    public static final String TODO_TABLE_SQL = "create table if NOT EXISTS " + TODO_TABLE_NAME + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, Title varchar(32) NOT NULL UNIQUE, " +
            "Content varchar(500) NOT NULL, Time long NOT NULL)";

    public static final String WEATHER_KEY = "b57a57e54732bf5c5b020302ae05b8cb";
    public static final String WEATHER_BASE_URL = "http://v.juhe.cn/weather/index?format=2&";

    public static final String WEATHER_BG_IMG = "http://alien95.cn/img/bg.jpg";

}
