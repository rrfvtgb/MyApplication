package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentAccueil extends Fragment {
    private static final String TAG = "FragmentAccueil";
    private MainActivity acti;

    /*
    public FragmentAccueil(MainActivity acti){
        this.acti=acti;
    }
*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_accueil, container, false);
        // Ton implem pour cet onglet ici
        /*
        try {
            acti.vueFormulaire.afficher();
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/

        //return acti.vueFormulaire;
        return view;
    }
}
