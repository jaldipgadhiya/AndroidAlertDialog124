package com.android.acadgild.androidalertdialog124.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.acadgild.androidalertdialog124.R;
import com.android.acadgild.androidalertdialog124.model.InfoData;


import java.util.ArrayList;

/**
 * Created by Jal on 20-07-2017.
 * Custom Adapter to display in Listview.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<InfoData> data;
    LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<InfoData> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_row, parent, false);
            holder = new ViewHolder();
            //holder.view = (TextView) convertView.findViewById(R.id.view);
            holder.toName = (TextView) convertView.findViewById(R.id.toName);
            holder.toPhoneNo = (TextView) convertView.findViewById(R.id.toPhoneNo);
            holder.toDob = (TextView) convertView.findViewById(R.id.toDob);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.toName.setText(data.get(position).getKeyName());
        holder.toPhoneNo.setText(data.get(position).getKeyPhone());
        holder.toDob.setText(data.get(position).getKeyDob());



        return convertView;
    }


    public class ViewHolder {
        //TextView view;
        TextView toName, toPhoneNo, toDob;
    }


}
