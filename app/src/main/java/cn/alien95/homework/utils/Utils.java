package cn.alien95.homework.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import cn.alien95.homework.BuildConfig;

/**
 * Created by linlongxin on 2016/1/15.
 */
public class Utils {

    private static Context mContext;
    private static String DEBUGTAG;

    public static void init(Context context){
        mContext=context;
    }

    public static void setDebugtag(String debugtag){
        DEBUGTAG=debugtag;
    }

    public static void Toast(String content){
        Toast.makeText(mContext,content,Toast.LENGTH_SHORT).show();
    }

    public static void ToastLong(String content){
        Toast.makeText(mContext,content,Toast.LENGTH_LONG).show();
    }

    public static void Log(String content){
        if(BuildConfig.DEBUG){
            Log.i(DEBUGTAG,content);
        }
    }

}
