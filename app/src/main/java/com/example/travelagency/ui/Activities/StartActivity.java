package com.example.travelagency.ui.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.travelagency.R;

public class StartActivity extends AppCompatActivity {

    ImageView bgstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        bgstart = (ImageView) findViewById(R.id.bgstart);

        bgstart.animate().scaleX(1.5f).scaleY(1.5f).setDuration(6000).start();
    }

    public void gotomain (View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

}
