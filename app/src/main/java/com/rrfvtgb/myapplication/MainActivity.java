package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.BottomNavigationView;
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

    private TextView mTextMessage;
    public String profil;
    private SelectionProfil vueProfil;
    private AffichageSysteme vueSysteme;
    public void creationAffichageSysteme()
    {
        vueSysteme = new AffichageSysteme(this);
    }
    public void creationSelectionProfil()
    {
        vueProfil = new SelectionProfil(this);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d("MainActivity", profil);
                    return true;
                case R.id.navigation_result:

                    //readJSON();
                    return true;
                case R.id.navigation_setup:
                    resetView();
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

    public void showJSON(){
        try {
            JSONObject j = readJSON();
            JSONArray services = j.getJSONArray("service");
            JSONObject service = services.getJSONObject(0);

            //mTextMessage.setText(service.optString("title"));
        }catch(JSONException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG );
        }
    }

    protected void resetView(){
        mLayout.removeAllViews();
    }

    protected void insertView(View view){
        mLayout.addView(view);
    }

    protected JSONObject readJSON(){


        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader( getAssets().open("service.json")));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        String result = text.toString();

        //Set the text

        try {
            JSONObject jObject = new JSONObject(result);
            return jObject;
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
            profil= readJSON().getJSONArray("service").getJSONObject(0).optString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mLayout = (LinearLayout) findViewById(R.id.layout);
        creationSelectionProfil();
        vueProfil.setTable(readJSON());
        creationAffichageSysteme();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        showJSON();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    public boolean onOptionItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_cpu:
                Log.d("Main Activity"," info systeme");
                resetView();
                try {
                    vueSysteme.afficher();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                insertView(vueSysteme);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
