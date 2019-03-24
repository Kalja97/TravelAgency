package com.example.travelagency.ui.Activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.travelagency.Fragment.SettingsFragment;
import com.example.travelagency.R;

import java.util.List;

public class SettingsActivity extends PreferenceActivity {


   
    @Override
    public void onBuildHeaders(List<Header> target) {

        loadHeadersFromResource(R.xml.header_preference, target);

    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingsFragment.class.getName().equals(fragmentName);
    }


}
