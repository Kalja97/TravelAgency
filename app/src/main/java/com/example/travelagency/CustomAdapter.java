package com.example.travelagency;

import android.app.Activity;
import android.content.Context;
import android.media.session.PlaybackState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

   Context context;
   List<RowItem> rowItems;

   CustomAdapter(Context context, List<RowItem> rowItems){
       this.context = context;
       this.rowItems = rowItems;
   }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    private class ViewHolder{
       ImageView country_pic;
       TextView country_name;
       TextView action_title;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.country_name = (TextView) convertView
                    .findViewById(R.id.country_name);
            holder.country_pic = (ImageView) convertView
                    .findViewById(R.id.country_pic);
            holder.action_title = (TextView) convertView.findViewById(R.id.action_title);


            RowItem row_pos = rowItems.get(position);

            holder.country_pic.setImageResource(row_pos.getCountry_pic_id());
            holder.country_name.setText(row_pos.getCountry_name());
            holder.action_title.setText(row_pos.getAction_title());


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}
