package com.example.diewithme;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diewithme.ConstructorMessages;
import com.example.diewithme.R;
import com.example.diewithme.ViewHolder;
import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<ViewHolder>  {

    ArrayList<ConstructorMessages> messages;
    LayoutInflater inflater;

    ConstructorMessages msg;
    View view;


    public DataAdapter(Context context, ArrayList<ConstructorMessages> messages) {
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.item_message, parent, false);

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        msg = messages.get(position);


        holder.authorItem.setText(msg.authors); // author
        holder.timeItem.setText(msg.times); // message
        holder.batteryItem.setText(msg.batteryLevel); // time
        holder.messageItem.setText(msg.messages); //battery
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
