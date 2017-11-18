package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements ProfilListener {
    private static final String TAG = "MainActivity";
    public String profil;
    public SelectionProfil vueProfil;
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private MonitorDialog dialog;

    public void creationSelectionProfil() {
        vueProfil = new SelectionProfil();

    }

    protected JSONObject readJSON(){
        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getAssets().open("service.json")));
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
        Log.d(TAG, "onCreate: Starting.");

        try {
            profil = readJSON().getJSONArray("service").getJSONObject(0).optString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog = new MonitorDialog(this);

        // Instantiate a ViewPager and a PagerAdapter.
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_dashboard_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_build_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_help_black_24dp);

        creationSelectionProfil();
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentAccueil(), getResources().getString(R.string.title_home));
        adapter.addFragment(new SelectionProfil(), getResources().getString(R.string.title_result));
        adapter.addFragment(new Fragment3(), getResources().getString(R.string.title_setup));
        adapter.addFragment(new Fragment4(), getResources().getString(R.string.title_help));
        viewPager.setAdapter(adapter);
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

                Log.d("MainActivity", "Action_cpu:selected");

                return true;
            default:
                Log.d("MainActivity", "Action Unknown: "+item.getTitle());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setProfil(JSONObject profil) throws JSONException {
        ProfilListener accueilFrag = (ProfilListener)
                getSupportFragmentManager().findFragmentById(R.id.tab1);

        if (accueilFrag != null) {
            // Call a method in the ArticleFragment to update its content
            accueilFrag.setProfil(profil);
        }
    }
}
