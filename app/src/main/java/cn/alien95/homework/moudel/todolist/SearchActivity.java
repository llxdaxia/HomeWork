package cn.alien95.homework.moudel.todolist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import cn.alien95.homework.R;
import cn.alien95.homework.app.BaseActivity;

/**
 * Created by linlongxin on 2016/1/17.
 */
public class SearchActivity extends BaseActivity {

    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist_activity_search);
        setToolbarIsBack(true);

        searchView = (SearchView) findViewById(R.id.search);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    public void handleIntent(Intent intent){
        //Intent.ACTION_VIEW  android:searchSuggestIntentAction="android.intent.action.VIEW"  还在这个view中
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // handles a click on a search suggestion; launches activity to show word
            intent.getData();


        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // handles a search query  Intent.ACTION_SEARCH这个Intent是在当我点击系统的搜索框右面的按钮时触发的
            String query = intent.getStringExtra(SearchManager.QUERY);
//            showResults(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            onSearchRequested();
        }
        return super.onOptionsItemSelected(item);
    }
}
