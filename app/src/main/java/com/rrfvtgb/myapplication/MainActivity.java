package com.rrfvtgb.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private static final String TAG = "MainActivity";
    private Frag_1_Home home;
    private Frag_2_Result vueProfil;
    private Frag_3_Setup setup;
    private View main_content;
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private MonitorDialog dialog;
    private String activeProfil;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        main_content = findViewById(R.id.main_content);

        dialog = new MonitorDialog(this);

        // Instantiate a ViewPager and a PagerAdapter.
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_dashboard_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_build_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_help_black_24dp);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());


        home      = new Frag_1_Home();
        vueProfil = new Frag_2_Result();
        setup     = new Frag_3_Setup();

        setup.preload(this);

        adapter.addFragment(home, getResources().getString(R.string.title_home));
        adapter.addFragment(vueProfil, getResources().getString(R.string.title_result));
        adapter.addFragment(setup, getResources().getString(R.string.title_setup));
        adapter.addFragment(new Frag_4_Help(), getResources().getString(R.string.title_help));

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
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getText() == getResources().getString(R.string.title_home)){
            if(activeProfil == null || !activeProfil.equals(setup.getActiveProfil()) ) {
                try {
                    home.setProfilData(setup.getProfilData());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                activeProfil = setup.getActiveProfil();
            }
        } else if(tab.getText() == getResources().getString(R.string.title_result)){
            hideSoftKeyboard(this);
            if(home != null && vueProfil != null){
                vueProfil.setData(home.computeValue());
            }
        } else hideSoftKeyboard(this);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}
}
