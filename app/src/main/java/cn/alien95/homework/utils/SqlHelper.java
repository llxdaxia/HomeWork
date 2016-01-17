package cn.alien95.homework.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class SqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static SqlHelper instance;

    public enum TableName {

        TODO_TABLE("ToDo");
        private String name;

        TableName(String name) {
            this.name = name;
        }
    }

    private SqlHelper(Context context, String dataBaseName) {
        super(context, dataBaseName, null, DATABASE_VERSION);
    }

    public static void init(Context context, String dataBaseName) {
        instance = new SqlHelper(context, dataBaseName);
    }

    public static SqlHelper getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        for (TableName table : TableName.values()) {
            sql = "create table if not exists " + table + "Id integer PRIMARY KEY AUTOINCREMENT, Title varchar(32) NOT NULL UNIQUE, " +
                    "Content varchar(500) NOT NULL, Time long NOT NULL";
            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            String sql;
            for (TableName table : TableName.values()) {
                sql = "DROP TABLE IF EXISTS " + table;
                db.execSQL(sql);
            }
            onCreate(db);
        }

    }

}
