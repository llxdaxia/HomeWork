package cn.alien95.homework.moudel.todolist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import cn.alien95.homework.R;
import cn.alien95.homework.app.BaseActivity;
import cn.alien95.homework.model.ToDoModel;
import cn.alien95.homework.model.bean.ToDo;
import cn.alien95.homework.moudel.about.AboutActivity;
import cn.alien95.homework.moudel.weather.WeatherActivity;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class ToDoListActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int REQUEST_CODE_ADD = 100;
    public static final String UPDATE_DATA = "UPDATE_DATA";
    public static final String INTENT_DATA = "INTENT_DATA";
    public static final String SEARCH_WORD = "SEARCH_WORD";
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist_activity_todolist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ToDoListActivity.this, AddToDoActivity.class), REQUEST_CODE_ADD);
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);   //这里的打开抽屉和关闭抽屉失效。。。。
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ToDoAdapter();
        recyclerView.setAdapter(adapter);
        adapter.addAll(ToDoModel.getInstance().getDataFromDB());
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == AddToDoActivity.RESULT_CODE_MODIFY_OR_ADD) {
            adapter.clear();
            adapter.addAll(ToDoModel.getInstance().getDataFromDB());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_todolist, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchMenuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("搜索");
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<ToDo> data = (ArrayList<ToDo>) ToDoModel.getInstance().
                        queryFromDB("Title LIKE ? OR Content LIKE ?", new String[]{"%" + query + "%", "%" + query + "%"},
                                null, null, null);
                Intent intent = new Intent();
                intent.putExtra(INTENT_DATA, data);
                intent.putExtra(SEARCH_WORD, query);
                intent.setClass(ToDoListActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.to_do_list:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.weather:
                startActivity(new Intent(this, WeatherActivity.class));
                break;
            case R.id.about_app:
                startActivity(new Intent(this, AboutActivity.class));
                break;

        }
        return true;
    }
}
