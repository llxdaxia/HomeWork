package cn.alien95.homework.moudel.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.alien95.homework.R;
import cn.alien95.homework.app.BaseActivity;

/**
 * Created by linlongxin on 2016/1/18.
 */
public class AboutActivity extends BaseActivity {

    @Bind(R.id.content)
    TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        ButterKnife.bind(this);
        setToolbarIsBack(true);
        content.setText("ToDoList && Weather\n"
                + "ToDoList 通过本地数据库来存取数据。\n"
                + "知识要点：\n" +
                "1,学习并熟悉掌握sql查询语句" +
                "2,自己去写一个SQLite工具类，来实现数据库的增删改查。 \n\n"
                + "Weather 学习网络请求，了解http请求过程。\n"
                + "知识要点：\n" +
                "1,学习并掌握网络get，post请求，并获取网络数据，解析数据成对象并显示。\n"
                + "2,网络获取图片，图片的优化，通过对这些的学习，自己摸索去写一些请求工具。\n\n"
        +"PostMan 继续学习网络\n"
        +"1，请求头设置\n"
        +"2，请求参数设置\n");
    }
}
