package cn.alien95.homework.moudel.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import cn.alien95.homework.R;
import cn.alien95.homework.app.BaseActivity;
import cn.alien95.homework.model.ToDoModel;
import cn.alien95.homework.model.bean.ToDo;
import cn.alien95.homework.utils.Utils;

/**
 * Created by linlongxin on 2016/1/15.
 */
public class AddToDoActivity extends BaseActivity {

    private EditText title;
    private EditText content;
    private ToDo todo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist_activity_add_todo);
        setToolbarIsBack(true);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        setTitle("添加");

        todo = (ToDo) getIntent().getSerializableExtra(ToDoListActivity.UPDATE_DATA);
        if (todo != null) {
            setTitle("编辑");
            title.setText(todo.getTitle());
            content.setText(todo.getContent());
            title.setSelection(todo.getTitle().length());
            content.setSelection(todo.getContent().length());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        String titleStr = title.getText().toString();
        String contentStr = content.getText().toString();
        if (item.getItemId() == R.id.finish) {
            if (todo != null) {
                int result = ToDoModel.getInstance().updateDataFromDB(new ToDo(todo.getId(), titleStr, contentStr, System.currentTimeMillis()));
                if (result == 0) {
                    Utils.SackbarShort(title, "标题或内容不能为空");
                    return true;
                }
            } else {
                long responseCode = ToDoModel.getInstance().insertDataToDB(new ToDo(titleStr, contentStr, System.currentTimeMillis()));
                if (responseCode == 0) {
                    Utils.SackbarShort(title, "标题或内容不能为空");
                    return true;
                } else if (responseCode == -1) {
                    Utils.SackbarShort(title, "标题重复");
                    return true;
                }
            }

            finish();
        }
        return true;
    }
}
