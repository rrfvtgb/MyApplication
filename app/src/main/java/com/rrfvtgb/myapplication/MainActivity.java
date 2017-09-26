package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    public JSONObject lectJSON(String nameOfFile)
    {
        try {
            ObjectInputStream o=new ObjectInputStream(new FileInputStream(nameOfFile));
            String chaine = (String)o.readObject();
            try {
                JSONObject j = new JSONObject(new JSONTokener(chaine));
                return j;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_result:
                    mTextMessage.setText(R.string.title_result);

                    readJSON();
                    return true;
                case R.id.navigation_setup:
                    mTextMessage.setText(R.string.title_setup);
                    return true;
                case R.id.navigation_help:
                    mTextMessage.setText(R.string.title_help);
                    return true;
            }
            return false;
        }

    };

    protected void readJSON(){
        File sdcard = Environment.getDataDirectory();

        //Get the text file
        File file = new File(sdcard,"file.txt");

        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
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
        mTextMessage.setText(result);

        try {
            JSONObject jObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
