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
public class ToDoModel extends Model {

    private static SQLiteDatabase db;

    public static ToDoModel getInstance() {
        return (ToDoModel) getInstance(ToDoModel.class);
    }

    //插入数据
    public void insertDataToDB(ToDo object) {
        if (object.getTitle().isEmpty() || object.getContent().isEmpty()) {
            Utils.Toast("标题或内容不能为空");
            return;
        }
        db = SqlHelper.getInstance().getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("Title", object.getTitle());
        content.put("Content", object.getContent());
        content.put("Time", object.getTime());
        db.insert(String.valueOf(SqlHelper.TableName.TODO_TABLE), null, content);
    }

    //删除数据
    public static void deleteDataFromDB(ToDo object) {
        db = SqlHelper.getInstance().getWritableDatabase();
        db.delete(String.valueOf(SqlHelper.TableName.TODO_TABLE), "Id = ?", new String[]{object.getId() + ""});
    }

    //修改数据
    public static void updateDataFromDB(ToDo object) {
        db = SqlHelper.getInstance().getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", object.getTitle());
        contentValues.put("Content", object.getContent());
        contentValues.put("Time", object.getTime());
        db.update(String.valueOf(SqlHelper.TableName.TODO_TABLE), contentValues, "Id = ?", new String[]{object.getId() + ""});
    }

    //查询数据
    public static List<ToDo> getDataFromDB() {
        db = SqlHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(String.valueOf(SqlHelper.TableName.TODO_TABLE),
                null,
                null,
                null,
                null, null, null);
        List<ToDo> toDoList = new ArrayList<ToDo>(cursor.getCount());
        Utils.Log("cursor_count:" + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                ToDo toDo = new ToDo();
                toDo.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                toDo.setContent(cursor.getString(cursor.getColumnIndex("Content")));
                toDo.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                toDo.setTime(cursor.getLong(cursor.getColumnIndex("Time")));
                toDoList.add(toDo);
            }
        }
        return toDoList;

    }

}
