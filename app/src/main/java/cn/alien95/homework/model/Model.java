package cn.alien95.homework.model;

/**
 * Created by linlongxin on 2016/1/15.
 */
public class Model {

    public static Model instance;

    public static<T extends Model> Model getInstance(Class model) {
        try {
            instance = (T) model.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
