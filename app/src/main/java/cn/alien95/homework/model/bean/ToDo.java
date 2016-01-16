package cn.alien95.homework.model.bean;

import java.io.Serializable;

/**
 * Created by linlongxin on 2016/1/5.
 */
public class ToDo implements Serializable {

    private int id;
    private String title;
    private String content;
    private long time;

    public ToDo() {
    }

    public ToDo(String title, String content, long time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public ToDo(int id, String title, String content, long time) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
