package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fabie on 09/10/2017.
 */

public class Frag_2_Result extends Fragment {
    private static final String TAG = "Frag_1_Home";

    JSONArray table;
    MainActivity acti;
    ListView resultView;
    String data = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_2__result, container, false);

        resultView = view.findViewById(R.id.result_view);

        if(data != null)
            this.loadData(data);

        return view;
    }

    public void setData(String data){
        this.data = data;

        if(resultView != null) {
            loadData(data);
        }
    }

    public void loadData(String data){
        //resultView.setText(data);
        List<String> values = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            Iterator<?> iterator = jsonObject.keys();
            String result;
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                result = key + ": " + jsonObject.getString(key);
                values.add(result);
            }
            //resultView.setText(result);
            ArrayAdapter adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.frag_2_listview, values);
            ListView listView = (ListView) getActivity().findViewById(R.id.result_view);
            listView.setAdapter(adapter);
            Toast.makeText(getActivity(), "Json Data has loaded", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void setTable(JSONObject j)
    {
        try {
            table=j.getJSONArray("service");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
/*
    public void afficher() throws Exception
    {
        //this.removeAllViews();
        int i;
        ArrayList<Button> listeButton;
        listeButton = new ArrayList<Button>();
        Button b = null;
        for(i=0;i<table.length();i++)
        {
            listeButton.add(new Button(this.getContext()));
            final String ch = table.getJSONObject(i).optString("title");
            listeButton.get(listeButton.size()-1).setText(ch.toCharArray(),0,ch.length());
            listeButton.get(listeButton.size()-1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acti.profil = new String(ch);
                }
            });
            //this.addView( listeButton.get(listeButton.size()-1));
        }

    }*/
}
