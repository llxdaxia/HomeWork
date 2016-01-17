package cn.alien95.homework.moudel.todolist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.alien95.homework.R;
import cn.alien95.homework.app.BaseActivity;
import cn.alien95.homework.model.ToDoModel;
import cn.alien95.homework.model.bean.ToDo;
import cn.alien95.homework.utils.Utils;

/**
 * Created by linlongxin on 2016/1/17.
 */
public class SearchActivity extends BaseActivity {

    @Bind(R.id.show_result)
    TextView showResult;
    @Bind(R.id.add_todo)
    TextView addTodo;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.linear_layout)
    LinearLayout linearLayout;
    @Bind(R.id.hint_search)
    TextView hintSearch;
    private List<ToDo> data;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist_activity_search);
        ButterKnife.bind(this);
        setToolbarIsBack(true);

        data = (List<ToDo>) getIntent().getSerializableExtra(ToDoListActivity.INTENT_DATA);
        Utils.Log("data:" + (data.isEmpty()));
        adapter = new ToDoAdapter();
        showResult(data, getIntent().getStringExtra(ToDoListActivity.SEARCH_WORD));

    }

    public void showResult(List<ToDo> data, String word) {
        if (data.isEmpty()) {
            hintSearch.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            showResult.setText(word + "无任务");
            addTodo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SearchActivity.this, AddToDoActivity.class));
                    finish();
                }
            });
        } else {
            linearLayout.setVisibility(View.GONE);
            hintSearch.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            if (!adapter.isEmpty()) {
                adapter.clear();
            }
            adapter.addAll(data);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            hintSearch.setText("搜索" + "\"" + word + "\"");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchMenuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("搜索");
        searchView.setIconifiedByDefault(true);
        searchView.setEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<ToDo> data = (ArrayList<ToDo>) ToDoModel.getInstance().queryFromDB("Title = ? OR Content = ?", new String[]{query});
                showResult(data, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<ToDo> data = (ArrayList<ToDo>) ToDoModel.getInstance().queryFromDB("Title = ? OR Content = ?", new String[]{newText});
                showResult(data, newText);
                return true;
            }
        });

        return true;
    }

}
