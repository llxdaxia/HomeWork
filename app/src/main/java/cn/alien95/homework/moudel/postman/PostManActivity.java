package cn.alien95.homework.moudel.postman;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.alien95.homework.R;
import cn.alien95.homework.app.BaseActivity;

/**
 * Created by linlongxin on 2016/1/18.
 */
public class PostManActivity extends BaseActivity {

    @Bind(R.id.http_url)
    EditText httpUrl;
    @Bind(R.id.btnMan)
    RadioButton btnMan;
    @Bind(R.id.btnWoman)
    RadioButton btnWoman;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.header)
    TextView header;
    @Bind(R.id.linear_layout_header)
    LinearLayout linearLayoutHeader;
    @Bind(R.id.params)
    TextView params;
    @Bind(R.id.linear_layout_params)
    LinearLayout linearLayoutParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postman_activity);
        ButterKnife.bind(this);
        setToolbarIsBack(true);

    }

    public void addItem(LinearLayout linearLayout){
        View item = LayoutInflater.from(this).inflate(R.layout.postman_item_params,null);
        ViewParent parent = item.getParent();
        if(parent instanceof ViewManager){
            ((ViewManager) parent).removeView(item);
        }
        linearLayout.addView(item);
    }
}
