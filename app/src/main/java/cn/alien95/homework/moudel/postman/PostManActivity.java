package cn.alien95.homework.moudel.postman;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

import alien95.cn.http.request.HttpRequest;
import alien95.cn.http.request.callback.HttpCallBack;
import alien95.cn.util.Utils;
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
    @Bind(R.id.add_header)
    TextView addHeader;
    @Bind(R.id.add_params)
    TextView addParams;
    @Bind(R.id.post)
    RadioButton post;
    @Bind(R.id.get)
    RadioButton get;
    @Bind(R.id.result)
    TextView result;

    private EditText key;
    private EditText value;

    private Map<String, String> headerMap = new HashMap<>();
    private Map<String, String> paramsMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postman_activity);
        ButterKnife.bind(this);
        setToolbarIsBack(true);


        addHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < linearLayoutHeader.getChildCount(); i++) {
                    if (!collectParamsOrHeader(linearLayoutHeader, i, headerMap))
                        return;
                }
                addItem(linearLayoutHeader);
            }
        });

        addParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.post) {
                    for (int i = 0; i < linearLayoutParams.getChildCount(); i++) {
                        if (!collectParamsOrHeader(linearLayoutParams, i, paramsMap))
                            return;
                    }
                    addItem(linearLayoutParams);
                }
            }
        });

    }


    public void addItem(LinearLayout linearLayout) {
        View item = LayoutInflater.from(this).inflate(R.layout.postman_item_params, null);
        ViewParent parent = item.getParent();
        if (parent instanceof ViewManager) {
            ((ViewManager) parent).removeView(item);
        }
        linearLayout.addView(item);
    }

    public boolean collectParamsOrHeader(LinearLayout linearLayout, int childIndex, Map<String, String> map) {
        View child = linearLayout.getChildAt(childIndex);
        if (child == null) return false;
        key = (EditText) child.findViewById(R.id.key);
        value = (EditText) child.findViewById(R.id.value);
        String keyStr = key.getText().toString();
        String valueStr = value.getText().toString();
        if (keyStr.isEmpty() || valueStr.isEmpty()) {
            return false;
        } else {
            map.put(keyStr, valueStr);
            return true;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_post_man, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.send) {
            if (httpUrl.getText().toString().isEmpty()) {
                Utils.SnackbarShort(httpUrl, "URL不能为空");
                return true;
            }
            showProgressBar("正在请求网络...");
            //header
            collectParamsOrHeader(linearLayoutHeader, linearLayoutHeader.getChildCount() - 1, headerMap);
            if (!headerMap.isEmpty()) {
                HttpRequest.getInstance().setHttpHeader(headerMap);
            }

            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                Utils.Log("header:" + entry.getKey() + "   " + entry.getValue());
            }

            if (radioGroup.getCheckedRadioButtonId() == R.id.post) {
                //POST请求
                Utils.Log("post请求:");
                collectParamsOrHeader(linearLayoutParams, linearLayoutParams.getChildCount() - 1, paramsMap);

                for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                    Utils.Log("params:" + entry.getKey() + "   " + entry.getValue());
                }

                HttpRequest.getInstance().post(httpUrl.getText().toString(),
                        paramsMap, new HttpCallBack() {
                            @Override
                            public void success(String info) {
                                result.setText(jsonFormatter(info));
                                dismissProgressBar();
                            }
                        });
            } else {
                //GET请求
                Utils.Log("get请求。");
                HttpRequest.getInstance().get(httpUrl.getText().toString(),
                        new HttpCallBack() {
                            @Override
                            public void success(String info) {
                                result.setText(jsonFormatter(info));
                                dismissProgressBar();
                            }
                        });
            }
        }
        return true;
    }

    public String jsonFormatter(String uglyJSONString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJSONString);
        return gson.toJson(je);
    }
}
