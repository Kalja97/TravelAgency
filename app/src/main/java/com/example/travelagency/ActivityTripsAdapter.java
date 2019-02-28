package com.example.travelagency;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ActivityTripsAdapter extends BaseAdapter {

    Context context;
    List<TripsActivityRowItem> activityTripsAdapter;

    ActivityTripsAdapter(Context context, List<TripsActivityRowItem> activityTripsAdapter){
        this.context = context;
        this.activityTripsAdapter = activityTripsAdapter;
    }

    @Override
    public int getCount() {
        return activityTripsAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return activityTripsAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return activityTripsAdapter.indexOf(getItem(position));
    }

    private class ViewHolder{
        TextView trip_name;
        TextView quickDescription;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.trip_name = (TextView) convertView
                    .findViewById(R.id.trip_name);

            holder.quickDescription = (TextView) convertView.findViewById(R.id.quickDescription);


            TripsActivityRowItem row_position = activityTripsAdapter.get(position);


            holder.trip_name.setText(row_position.getTrip_name());
            holder.quickDescription.setText(row_position.getQuickDescription_title());


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}
