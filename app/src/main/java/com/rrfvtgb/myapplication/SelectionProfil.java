package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fabie on 09/10/2017.
 */

public class SelectionProfil extends Fragment {
    private static final String TAG = "FragmentAccueil";

    JSONArray table;
    MainActivity acti;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.setup, container, false);

        //Implem ici

        return view;
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
    public SelectionProfil(MainActivity acti) {
        super(acti);
        this.acti=acti;
        this.setOrientation(LinearLayout.VERTICAL);

    }
*/
}
