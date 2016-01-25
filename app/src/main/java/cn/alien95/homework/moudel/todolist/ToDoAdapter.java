package cn.alien95.homework.moudel.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import alien95.cn.util.MessageNotify;
import alien95.cn.util.TimeTransform;
import cn.alien95.homework.R;
import cn.alien95.homework.model.ToDoModel;
import cn.alien95.homework.model.bean.ToDo;

/**
 * Created by linlongxin on 2016/1/7.
 */
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private List<ToDo> toDoList = new ArrayList<>();

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ToDoViewHolder(parent.getContext(), R.layout.todolist_item_todo);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        holder.setData(toDoList.get(position));
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public void add(ToDo toDo) {
        toDoList.add(toDo);
        notifyDataSetChanged();
    }

    public void addToFirst(ToDo toDo) {
        toDoList.add(0, toDo);
        notifyDataSetChanged();
    }

    public void addAll(List<ToDo> list) {
        toDoList.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(ToDo toDo) {
        toDoList.remove(toDo);
        notifyDataSetChanged();
    }

    public void clear() {
        toDoList.clear();
    }

    public boolean isEmpty() {
        if (toDoList.isEmpty()) {
            return true;
        } else return false;
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView content;
        private TextView time;

        public ToDoViewHolder(Context context, @LayoutRes int layout) {
            super(LayoutInflater.from(context).inflate(layout, null));
            content = (TextView) itemView.findViewById(R.id.content);
            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);

        }

        public void setData(final ToDo object) {
            title.setText(object.getTitle());
            content.setText(object.getContent());
            time.setText(TimeTransform.getInstance().transformRecentDate(object.getTime()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AddToDoActivity.class);
                    intent.putExtra(ToDoListActivity.UPDATE_DATA, object);
                    ((Activity) v.getContext()).startActivityForResult(intent, ToDoListActivity.REQUEST_CODE_ADD);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("是否删除");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToDoModel.getInstance().deleteDataFromDB(object);
                            toDoList.remove(object);
                            notifyDataSetChanged();
                            if (toDoList.isEmpty())
                                MessageNotify.getInstance().sendMessage();
                        }
                    });
                    builder.setNegativeButton("否", null);
                    builder.create().show();
                    return true;
                }
            });
        }

    }
}
