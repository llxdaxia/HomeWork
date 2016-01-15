package cn.alien95.homework.model.bean;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class ToDo {

    private int id;
    private String title;
    private String content;
    private long time;

    public ToDo(){}

    public ToDo(String title,String content,long time){
        this.title = title;
        this.content = content;
        this.time = time;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
