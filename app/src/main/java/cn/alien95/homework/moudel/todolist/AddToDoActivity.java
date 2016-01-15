package cn.alien95.homework.moudel.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import cn.alien95.homework.R;
import cn.alien95.homework.app.BaseActivity;
import cn.alien95.homework.model.ToDoModel;
import cn.alien95.homework.model.bean.ToDo;

/**
 * Created by linlongxin on 2016/1/15.
 */
public class AddToDoActivity extends BaseActivity {

    private TextView title;
    private TextView content;
    private TextView time;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        setToolbarIsBack(true);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        time = (TextView) findViewById(R.id.time);
        setTitle("添加");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        String titleStr = title.getText().toString();
        String contentStr = content.getText().toString();
        if (item.getItemId() == R.id.finish) {
            ToDoModel.getInstance().insertDataToDB(new ToDo(titleStr, contentStr, System.currentTimeMillis()));
        }
        return true;
    }
}
