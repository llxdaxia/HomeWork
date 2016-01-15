package cn.alien95.homework.moudel.todolist;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.alien95.homework.R;
import cn.alien95.homework.model.bean.ToDo;

/**
 * Created by linlongxin on 2016/1/7.
 */
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {


    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ToDoViewHolder(parent.getContext(), R.layout.item_todo);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView content;
        private TextView time;

        public ToDoViewHolder(Context context,@LayoutRes int layout) {
            super(LayoutInflater.from(context).inflate(layout,null));
            content = (TextView) itemView.findViewById(R.id.content);
            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
        }

        public void setData(ToDo object){
            title.setText(object.getTitle());
            content.setText(object.getContent());
            time.setText(object.getTime() + "");
        }

    }
}
