package com.example.memo.Files;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.example.memo.Files.FilesItem;
import com.example.memo.R;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FilesAdapter extends BaseAdapter implements Filterable {
//    private int newResourceId;

    private int resoureId;
    private Context context;
    private List<FilesItem> FileItemList; //这个数据是会改变的，所以要有个变量来备份一下原始数据
    private List<FilesItem> backFileItemList; //用来备份原始数据


    MyFilter mFilter ;

    public FilesAdapter(Context context , List<FilesItem> itemList) {
        this.context = context;
        this.FileItemList = itemList;
        backFileItemList = itemList;

    }

    @Override
    public int getCount() {
        return FileItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    //用来优化的viewholder内部类，优化控件findviewbyid
    class ViewHolder {
        TextView file_title;
        TextView file_subtitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.file_item,null);
            holder = new ViewHolder();
            holder.file_title = (TextView) convertView.findViewById(R.id.file_title);
            holder.file_subtitle = (TextView) convertView.findViewById(R.id.file_subtitle);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.file_title.setText(FileItemList.get(position).getTitle());
        holder.file_subtitle.setText(FileItemList.get(position).getSubtitle());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter ==null){
            mFilter = new MyFilter();
        }
        return mFilter;
    }
    //我们需要定义一个过滤器的类来定义过滤规则
    class MyFilter extends Filter {
        //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<FilesItem> list ;
            if (TextUtils.isEmpty(charSequence)){//当过滤的关键字为空的时候，我们则显示所有的数据
                list  = backFileItemList;
            }else {//否则把符合条件的数据对象添加到集合中
                list = new ArrayList<>();
                for (FilesItem fileItem:backFileItemList){
                    if (fileItem.getTitle().contains(charSequence)){ //要匹配的item中的view
                        list.add(fileItem);
                    }

                }
            }
            result.values = list; //将得到的集合保存到FilterResults的value变量中
            result.count = list.size();//将集合的大小保存到FilterResults的count变量中

            return result;
        }
        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            FileItemList = (List<FilesItem>)filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();//通知数据发生了改变
            }else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }
    }

//    public FilesAdapter(Context context, int resourceId, List<FilesItem> FilesItemsList){
//        super(context, resourceId, FilesItemsList);
//        newResourceId = resourceId;
//    }

//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent){
//
//        FilesItem fi = (FilesItem) getItem (position);
//        View view = LayoutInflater.from (getContext()).inflate (newResourceId, parent, false);
//
//        TextView title = view.findViewById (R.id.file_title);
//        TextView subtitle = view.findViewById (R.id.file_subtitle);
//
//        title.setText (fi.getTitle());
//        subtitle.setText (fi.getSubtitle());
//        return view;
//    }
}
