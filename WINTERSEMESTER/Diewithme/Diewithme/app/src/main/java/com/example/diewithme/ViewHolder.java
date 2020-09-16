package com.example.diewithme;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewHolder extends RecyclerView.ViewHolder {


    TextView messageItem;
    TextView timeItem;
    TextView authorItem;
    TextView batteryItem;
    LinearLayout itemLinear;

    DataAdapter time;

    public ViewHolder(View itemView) {
        super(itemView);

        authorItem = itemView.findViewById(R.id.authorItem);
        timeItem = itemView.findViewById(R.id.timeItem);
        messageItem = itemView.findViewById(R.id.messageItem);
        batteryItem = itemView.findViewById(R.id.batteryItem);
    }
}