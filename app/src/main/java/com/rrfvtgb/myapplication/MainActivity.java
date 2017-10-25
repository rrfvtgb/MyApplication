package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MonitorDialog dialog;
    private Formulaire vueFormulaire;
    public String profil;
    private SelectionProfil vueProfil;

    public void creationFormulaire()
    {
        vueFormulaire = new Formulaire(this);
    }

    public void creationSelectionProfil() {
        vueProfil = new SelectionProfil(this);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d("MainActivity", profil);
                    try {
                        vueFormulaire.afficher();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                  //  insertView(vueFormulaire);
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
        }
    }

    protected JSONObject readJSON(){


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
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new FragmentAccueil(), getResources().getString(R.string.title_home));
        adapter.addFragment(new FragmentAccueil(), getResources().getString(R.string.title_result));
        adapter.addFragment(new FragmentAccueil(), getResources().getString(R.string.title_setup));
        adapter.addFragment(new FragmentAccueil(), getResources().getString(R.string.title_help));
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_dashboard_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_build_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_help_black_24dp);

        creationSelectionProfil();
        vueProfil.setTable(readJSON());
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        creationFormulaire();
        vueFormulaire.setTable(readJSON());
        showJSON();
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

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
