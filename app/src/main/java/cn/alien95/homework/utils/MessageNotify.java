package cn.alien95.homework.utils;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linlongxin on 2016/1/19.
 * 在两个没有任何联系的类里面实现通讯，类似EventBus
 */
public class MessageNotify {

    private static MessageNotify instance;

    private Map<Object, Method> map = new HashMap<>();

    private MessageNotify() {
    }

    public static MessageNotify getInstance() {
        if (instance == null) {
            synchronized (MessageNotify.class) {
                instance = new MessageNotify();
            }
        }
        return instance;
    }

    //
    public void registerEvent(Context context, Method method) {
        map.put(context, method);
    }

    private void executeEvent() {
        for (Map.Entry<Object, Method> entry : map.entrySet()) {
            try {
                entry.getValue().invoke(entry.getKey(), new Object[]{});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage() {
        executeEvent();
    }


}
