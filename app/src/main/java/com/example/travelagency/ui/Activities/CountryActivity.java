package com.example.travelagency.ui.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.travelagency.Adapters.Country;
import com.example.travelagency.Adapters.CountryAdapter;
import com.example.travelagency.R;

import java.util.ArrayList;
import java.util.List;

public class CountryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Country> songList;
    private CountryAdapter songAdapter;

    String[] countrynames = {"Spanien", "Scotland", "Spain", "Switzerland", "Frankreich", "China"};

    String[] languages = {"Spanisch", "Englisch", "Spanisch", "Deutsch", "Französisch", "Chinesisch"};

    String[] descriptions = {"A", "B", "C", "D", "E", "F"};

    int[] inhabitans = {2000, 15332, 454854, 54596, 572549, 7545454};


    int[] pics = {
            R.drawable.barcelonaview,
            R.drawable.scotland,
            R.drawable.spain,
            R.drawable.switzerland,
            R.drawable.switzerland,
            R.drawable.switzerland};

    //Menü-Items auf der Actionbar anbringen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //Use this setting to improve performance if you know that changes in
        //the content do not change the layout size of the RecyclerView
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

//        //using a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);

//        use this in case of gridlayoutmanager
//        mLayoutManager = new GridLayoutManager(this,2);

//        use this in case of Staggered GridLayoutManager
//        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);


        //wiring up layoutmanager to our android recyclerview
        mRecyclerView.setLayoutManager(mLayoutManager);

        //intializing an arraylist called songlist
        songList = new ArrayList<>();

        //adding data from arrays to songlist
        for (int i = 0; i < countrynames.length; i++) {
            Country country = new Country(countrynames[i], languages[i], descriptions[i], inhabitans[i], i + 1, pics[i]);
            songList.add(country);
        }
        //initializing adapter
        songAdapter = new CountryAdapter(songList);

        //specifying an adapter to access data, create views and replace the content
        mRecyclerView.setAdapter(songAdapter);
        songAdapter.notifyDataSetChanged();

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(CountryActivity.this, "Card at " + position + " is clicked", Toast.LENGTH_SHORT).show();
                nextpage(view);
            }
        }));
    }

    public void nextpage(View view){
        Intent intent = new Intent (this, TripsActivity.class);
        startActivity(intent);
    }

    //Actions für Actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add:
                Intent intentHome = new Intent(this, addLocationActivity.class);
                startActivity(intentHome);
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}