package com.example.memo.TODO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.memo.Agenda.FirstEntity;
import com.example.memo.Agenda.SecondAdapter;
import com.example.memo.Agenda.SecondEntity;
import com.example.memo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MC on 16/3/18.
 */
public class TodoFirstAdapter extends BaseAdapter {

    Context context;
    List<FirstEntity> firstListData = new ArrayList<FirstEntity>();

    public TodoFirstAdapter(Context context, List<FirstEntity> firstListData) {
        this.context = context;
        this.firstListData = firstListData;
    }

    @Override
    public int getCount() {
        return firstListData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        FirstList holder = null;
        if (view == null) {
            holder = new FirstList();
            view = LayoutInflater.from(context).inflate(R.layout.todo_add_time, null);
            holder.textdate = (TextView) view.findViewById(R.id.text_file);
            holder.secondList = (ListView) view.findViewById(R.id.list_todo_time);
            view.setTag(holder);
        } else {
            holder = (FirstList) view.getTag();
        }

        holder.textdate.setText(firstListData.get(position).getUserName());

        List<SecondEntity> listSecondList = firstListData.get(position).getSecondList();
        SecondAdapter secondAdapter = new SecondAdapter(context, listSecondList);
        holder.secondList.setAdapter(secondAdapter);
        secondAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(holder.secondList);


        return view;

    }


    class FirstList {
        TextView textdate;
        ListView secondList;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
