package cn.alien95.homework.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.alien95.homework.model.bean.ToDo;
import cn.alien95.homework.utils.SqlHelper;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class ToDoModel extends Model {

    private static SQLiteDatabase db;

    public static ToDoModel getInstance() {
        return (ToDoModel) getInstance(ToDoModel.class);
    }

    //插入数据
    public long insertDataToDB(ToDo object) {
        if (object.getTitle().isEmpty() || object.getContent().isEmpty()) {
            return 0;
        }
        db = SqlHelper.getInstance().getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("Title", object.getTitle());
        content.put("Content", object.getContent());
        content.put("Time", object.getTime());
        return db.insert(String.valueOf(SqlHelper.TableName.TODO_TABLE), null, content);
    }

    //删除数据
    public int deleteDataFromDB(ToDo object) {
        db = SqlHelper.getInstance().getWritableDatabase();
        return db.delete(String.valueOf(SqlHelper.TableName.TODO_TABLE), "Id = ?", new String[]{object.getId() + ""});
    }

    //修改数据
    public int updateDataFromDB(ToDo object) {
        db = SqlHelper.getInstance().getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", object.getTitle());
        contentValues.put("Content", object.getContent());
        contentValues.put("Time", object.getTime());
        return db.update(String.valueOf(SqlHelper.TableName.TODO_TABLE), contentValues, "Id = ?", new String[]{object.getId() + ""});
    }

    //查询数据
    public List<ToDo> queryFromDB(String whereClause, String[] whereArgs,
                                  String groupBy, String having,
                                  String orderBy) {
        db = SqlHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(String.valueOf(SqlHelper.TableName.TODO_TABLE),
                null,
                whereClause,
                whereArgs,
                groupBy, having, orderBy);
        cursor.moveToFirst();

        List<ToDo> toDoList = new ArrayList<ToDo>(cursor.getCount());
        if (cursor.getCount() > 0) {
            do {
                ToDo toDo = new ToDo();
                toDo.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                toDo.setContent(cursor.getString(cursor.getColumnIndex("Content")));
                toDo.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                toDo.setTime(cursor.getLong(cursor.getColumnIndex("Time")));
                toDoList.add(toDo);

            } while (cursor.moveToNext());
        }
        return toDoList;
    }

    //通过时间降序排序过去TODOLIST列表
    public List<ToDo> getDataFromDB() {
        String timeOrder = "Time DESC";
        return queryFromDB(null, null, null, null, timeOrder);
    }

    public boolean clearDataFromDataBase() {
        db = SqlHelper.getInstance().getWritableDatabase();
        return db.delete(String.valueOf(SqlHelper.TableName.TODO_TABLE), null, null) > 0;
    }

}
