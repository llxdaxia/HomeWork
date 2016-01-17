package cn.alien95.homework.moudel.todolist;

import android.content.Intent;
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

    public static final String MODIFY_OR_ADD = "MODIFY_OR_ADD";
    public static final int RESULT_CODE_MODIFY_OR_ADD = 101;
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
        ToDo object;
        if (item.getItemId() == R.id.finish) {
            if (todo != null) {
                int result = ToDoModel.getInstance().updateDataFromDB(object = new ToDo(todo.getId(), titleStr, contentStr, System.currentTimeMillis()));
                if (result == 0) {
                    if (titleStr.isEmpty() || contentStr.isEmpty()) {
                        Utils.SackbarShort(title, "标题或内容不能为空");
                    } else {
                        Utils.Toast("没有修改");
                    }
                    return true;
                }
            } else {
                long responseCode = ToDoModel.getInstance().insertDataToDB(object = new ToDo(titleStr, contentStr, System.currentTimeMillis()));
                if (responseCode == 0) {
                    Utils.SackbarShort(title, "标题或内容不能为空");
                    return true;
                } else if (responseCode == -1) {
                    Utils.SackbarShort(title, "标题重复");
                    return true;
                }

            }
            Intent intent = new Intent();
            setResult(RESULT_CODE_MODIFY_OR_ADD, intent);
            intent.putExtra(MODIFY_OR_ADD, object);
            finish();
        }
        return true;
    }
}
