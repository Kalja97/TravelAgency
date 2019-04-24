package com.example.travelagency.ui.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.travelagency.R;
import com.example.travelagency.ui.Activities.location.LocationsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class StartActivity extends AppCompatActivity {

    ImageView bgstart;
    private final String TAG = "Test";

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Get the registration token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = "Token:" + token;
                        Log.d(TAG, msg);
                        Toast.makeText(StartActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        //zooming picture
        bgstart = (ImageView) findViewById(R.id.bgstart);
        bgstart.animate().scaleX(1.5f).scaleY(1.5f).setDuration(6000).start();
    }

    //Method to show the locations
    public void gotomain (View view){
        Intent intent = new Intent (this, LocationsActivity.class);
        startActivity(intent);
    }



}
