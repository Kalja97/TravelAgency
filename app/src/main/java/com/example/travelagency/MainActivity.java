package com.example.travelagency;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] country_names;
    TypedArray country_pics;
    String[] action_title;

    List<MainPageRowItem> mainPageRowItems;
    ListView mylistview;

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

        mainPageRowItems = new ArrayList<MainPageRowItem>();

        country_names = getResources().getStringArray(R.array.country_names);

        country_pics = getResources().obtainTypedArray(R.array.country_pics);

        action_title = getResources().getStringArray(R.array.action_title);



        for (int i = 0; i < country_names.length; i++) {
            MainPageRowItem item = new MainPageRowItem(country_names[i],
                    country_pics.getResourceId(i, -1), action_title[i]);
            mainPageRowItems.add(item);
        }

        mylistview = (ListView) findViewById(R.id.list);
        MainPageAdapter adapter = new MainPageAdapter(this, mainPageRowItems);
        mylistview.setAdapter(adapter);

        mylistview.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String member_name = mainPageRowItems.get(position).getCountry_name();
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, TripsActivity.class);
        startActivity(intent);
    }

    public void addCountry (View view){
        Intent intent = new Intent (this, addLocationActivity.class);
        startActivity(intent);
    }

    //Actions für Actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add:
                Intent intentHome = new Intent(this, addLocationActivity.class);
                startActivity(intentHome);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

