package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.view.LayoutInflater;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLayout;
    private static final int NUM_PAGES = 4;
    private MonitorDialog dialog;

    private TextView mTextMessage;
    public String profil;
    private SelectionProfil vueProfil;
    private AffichageSysteme vueSysteme;
    public void creationAffichageSysteme()
    {
        vueSysteme = new AffichageSysteme(this);
    }

    public void creationSelectionProfil() {
        vueProfil = new SelectionProfil(this);

    }

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            resetView();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d("MainActivity", profil);
                    return true;
                case R.id.navigation_result:
                    //readJSON();
                    return true;
                case R.id.navigation_setup:
                    try {
                        vueProfil.afficher();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    insertView(vueProfil);
                    return true;
                case R.id.navigation_help:
                    return true;
            }
            return false;
        }

    };

    public void showJSON() {
        try {
            JSONObject j = readJSON();
            JSONArray services = j.getJSONArray("service");
            JSONObject service = services.getJSONObject(0);

            //mTextMessage.setText(service.optString("title"));
        } catch (JSONException e) {
            Toast
                    .makeText(this, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    protected void resetView() {
        mLayout.removeAllViews();
    }

    protected void insertView(View view) {
        mLayout.addView(view);
    }

    protected JSONObject readJSON() {


        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("service.json")));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            //You'll need to add proper error handling here
        }

        String result = text.toString();

        //Set the text

        try {
            return new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        try {
            profil = readJSON().getJSONArray("service").getJSONObject(0).optString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog = new MonitorDialog(this);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new MainActivity.ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mLayout = (LinearLayout) findViewById(R.id.layout);
        creationSelectionProfil();
        vueProfil.setTable(readJSON());
        creationAffichageSysteme();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        showJSON();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cpu:
                dialog.show();

                Toast
                        .makeText(this, "Display Dialog:Monitor", Toast.LENGTH_LONG)
                        .show();

                Log.d("MainActivity", "Action_cpu:selected");

                return true;
            default:
                Log.d("MainActivity", "Action Unknown: "+item.getTitle());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
