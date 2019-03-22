package com.example.travelagency.ui.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.travelagency.Adapters.MainPageAdapter;
import com.example.travelagency.Entities.Location;
import com.example.travelagency.R;
import com.example.travelagency.RowItems.MainPageRowItem;
import com.example.travelagency.viewmodel.LocationListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private ListView listview;
    private List<Location> showList;
    private LocationListViewModel viewModel;


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
        setContentView(R.layout.activity_main);
        setTitle("Locations");

        /*
            listview = (ListView) findViewById(R.id.listview);
            String[] shows = getResources().getStringArray(R.array.shows_array);
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shows);
            listview.setAdapter(adapter);
        */

        listview = findViewById(R.id.listview);
        showList = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        LocationListViewModel.Factory factory = new LocationListViewModel.Factory(getApplication(), "CountryName");
        viewModel = ViewModelProviders.of(this, factory).get(LocationListViewModel.class);
        viewModel.getLocation().observe(this, showEntities -> {
            if (showEntities != null) {
                showList = (List<Location>) showEntities;
                adapter.addAll(showList);
            }
        });
        listview.setAdapter(adapter);
        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ShowDetails.class);
        *//*
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                        Intent.FLAG_ACTIVITY_NO_HISTORY
                );
        *//*
                intent.putExtra("showName", showList.get(position).getName());
                startActivity(intent);
            }
        });*/
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






