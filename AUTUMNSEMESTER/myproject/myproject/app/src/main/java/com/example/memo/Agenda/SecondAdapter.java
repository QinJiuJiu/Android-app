package com.example.memo.Agenda;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.memo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MC on 16/3/18.
 */
public class SecondAdapter extends BaseAdapter {

    List<SecondEntity> listData = new ArrayList<SecondEntity>();
    Context context;

    public SecondAdapter(Context context, List<SecondEntity> listData){
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
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
    public View getView(int position, View view, ViewGroup parent) {
        SecondList holder = null;
        if(view == null){
            holder = new SecondList();
            view = LayoutInflater.from(context).inflate(R.layout.item_agenda, null);
            holder.filename  = (TextView) view.findViewById(R.id.filename);
            holder.titlename = (TextView) view.findViewById(R.id.titlename);
            holder.state = (TextView) view.findViewById(R.id.agenda_state);
            view.setTag(holder);
        }else {
            holder = (SecondList)view.getTag();
        }

        holder.filename.setText(listData.get(position).getParam1());
        holder.titlename.setText(listData.get(position).getParam2());
        holder.state.setText(listData.get(position).getParam3());
        Log.i("KKKKKK:",""+listData.size());
        return view;
    }

    class SecondList{
        TextView filename;
        TextView titlename;
        TextView state;
    }


}
