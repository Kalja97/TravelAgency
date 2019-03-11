package com.example.travelagency.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelagency.R;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private List<Country> songList;

    //Provide a reference to the views for each data item
    //Complex data items may need more than one view per item, and
    //you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //each data item is just a string in this case
        public TextView tvRank, countryname, language, inhabitant;
        public ImageView pic;

        public ViewHolder(View v) {
            super(v);
            tvRank = (TextView)v.findViewById(R.id.tv_rank);
            countryname = (TextView) v.findViewById(R.id.countryname);
            language = (TextView) v.findViewById(R.id.language);
            inhabitant = (TextView) v.findViewById(R.id.inhabitant);
            pic = (ImageView) v.findViewById(R.id.pic);

        }
    }

    //Provide a suitable constructor
    public CountryAdapter(List<Country> songList){
        this.songList = songList;
    }

    //Create new views (invoked by the layout manager)
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_item,parent,false);

        //set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Replace the contents of a view (invoked by the layout manager
    @Override
    public void onBindViewHolder(CountryAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        Country song = songList.get(position);
        holder.tvRank.setText(String.valueOf(song.getRank()));
        holder.countryname.setText(song.getCountryname());
        holder.language.setText(song.getLanguage());
        holder.pic.setImageResource(song.getPic());
        holder.inhabitant.setText(String.valueOf(song.getInhabitant()));
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }
}
