package cn.alien95.homework.model.bean;

/**
 * Created by linlongxin on 2016/1/18.
 */
public class WeatherData {

    public int resultcode;
    public Result result;

    public class Result {
        public Sk sk;
        public Today today;
        public Future[] future;
    }

    public class Sk {
        public int temp;
        public String wind_direction;
        public String wind_strength;
        public String humidity;
        public String time;
    }

    public class Today {
        public String weather;
        public String temperature;
        public String wind;
        public String week; //"星期一",
        public String city; //"苏州",
        public String date_y; //"2016年01月18日",
        public String dressing_index;  //"冷",
        public String dressing_advice;  //"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。",
        public String uv_index; //"最弱";
        public String wash_index; //较不宜",
        public String travel_index; // "较不宜",
        public String exercise_index; // "较不宜",
    }

    public class Future {
        public String temperature;
        public String weather;
        public String wind;
        public String week;
        public String date;

    }

}
