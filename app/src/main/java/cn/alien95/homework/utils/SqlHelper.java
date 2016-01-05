package cn.alien95.homework.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class SqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private String DATABASE_NAME;
    private String TABLE_NAME;


    public SqlHelper(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
        DATABASE_NAME = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
