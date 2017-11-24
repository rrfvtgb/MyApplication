package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fabie on 09/10/2017.
 */

public class Frag_2_Result extends Fragment {
    private static final String TAG = "Frag_1_Home";

    JSONArray table;
    MainActivity acti;
    TextView resultView;
    String data = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_2_result, container, false);

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
        resultView.setText(data);
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
        this.removeAllViews();
        int i;
        ArrayList<Button> listeButton;
        listeButton = new ArrayList<Button>();
        Button b = null;
        for(i=0;i<table.length();i++)
        {
            listeButton.add(new Button(this.getContext()));
            final String ch = table.getJSONObject(i).optString("title");
            listeButton.get(listeButton.size()-1).setText(ch.toCharArray(),0,ch.length());
            listeButton.get(listeButton.size()-1).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    acti.profil = new String(ch);
                }
            });
            this.addView( listeButton.get(listeButton.size()-1));
        }

    }
    public Frag_2_Result(MainActivity acti) {
        super(acti);
        this.acti=acti;
        this.setOrientation(LinearLayout.VERTICAL);

    }
*/
}
