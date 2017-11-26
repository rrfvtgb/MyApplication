package com.rrfvtgb.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Frag_3_Setup extends Fragment {
    private static final String TAG = "Frag_3_Home";

    protected Map<String, JSONObject> profils = new HashMap<>();
    protected ArrayList<String> profils_names = new ArrayList<>();

    protected Spinner profil_spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_3__setup, container, false);

        load();

        profil_spinner = view.findViewById(R.id.profil_select);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                R.layout.frag_3_profil_item, profils_names);
        profil_spinner.setAdapter(adapter);


        return view;
    }

    protected void load(){
        if(profils.isEmpty() && this.getContext() != null) {
            preload(this.getContext());
        }
    }

    public void preload(Context c){
        try {
            populateMap(c);

            for (String name: profils.keySet()){
                profils_names.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getActiveProfil(){
        if(profil_spinner != null) {
        return (String) profil_spinner.getSelectedItem();
        }else {
            return profils_names.get(0);
        }
    }

    public JSONObject getProfilData(){
        load();

        return profils.get(this.getActiveProfil());
    }

    protected void populateMap(Context c) throws JSONException {
        JSONObject data = readJSON(c);

        Log.i(TAG, data.toString());

        JSONArray services = data.optJSONArray("service");

        for(int i = 0; i<services.length(); i++){
            JSONObject s = services.getJSONObject(i);

            String title = s.getString("title");

            Log.i(TAG, title);
            profils.put(title, s);
        }

    }

    protected InputStream JSONStream(Context c) throws IOException {
        File cache = new File(c.getCacheDir(), "service.json");

        if( cache.exists()) {
            return new FileInputStream(cache);
        }else{
            return c.getAssets().open("service.json");
        }
    }

    protected JSONObject readJSON(Context c) {
        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(JSONStream(c)));
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
}
