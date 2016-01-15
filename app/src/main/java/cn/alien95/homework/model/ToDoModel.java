package cn.alien95.homework.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.alien95.homework.model.bean.ToDo;
import cn.alien95.homework.utils.SqlHelper;
import cn.alien95.homework.utils.Utils;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class ToDoModel extends Model{

    private SQLiteDatabase db;

    public static ToDoModel getInstance() {
        return (ToDoModel) getInstance(ToDoModel.class);
    }

    //插入数据
    public void insertDataToDB(ToDo object) {
        if(object.getTitle().isEmpty() || object.getContent().isEmpty()){
            Utils.Toast("标题或内容不能为空");
            return;
        }
        db = SqlHelper.getInstance().getWritableDatabase();
        db.beginTransaction();
        ContentValues content = new ContentValues();
        content.put("title", object.getTitle());
        content.put("content", object.getContent());
        content.put("time", object.getTime());
        db.insert(String.valueOf(SqlHelper.TableName.TODO_TABLE), null, content);
        db.setTransactionSuccessful();
    }

    //删除数据
    public void deleteDataFromDB(ToDo object){
        db = SqlHelper.getInstance().getWritableDatabase();
        db.beginTransaction();
        db.delete(String.valueOf(SqlHelper.TableName.TODO_TABLE),"title",new String[]{object.getTitle()});
        db.setTransactionSuccessful();
    }

    public void getDataFromDB(){
        db = SqlHelper.getInstance().getReadableDatabase();
        db.beginTransaction();
        int count;
        Cursor cursor = db.query(String.valueOf(SqlHelper.TableName.TODO_TABLE),
                null,
                null,
                null,
                null, null, null);
        if (cursor.getCount() > 0) {
            List<ToDo> orderList = new ArrayList<ToDo>(cursor.getCount());
            while (cursor.moveToNext()) {
                ToDo order = cursor.getExtras(cursor);
                orderList.add(order);
            }
            return orderList;
        }
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        db.setTransactionSuccessful();
    }

}
