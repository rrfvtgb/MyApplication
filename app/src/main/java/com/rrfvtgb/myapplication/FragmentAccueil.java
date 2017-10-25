package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

public class FragmentAccueil extends Fragment {
    private MainActivity acti;
    public FragmentAccueil(MainActivity acti){
        this.acti=acti;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            acti.vueFormulaire.afficher();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return acti.vueFormulaire;
        //return inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
    }
}
