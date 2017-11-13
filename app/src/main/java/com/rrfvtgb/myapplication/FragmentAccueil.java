package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.json.JSONException;

public class FragmentAccueil extends Fragment {
    private static final String TAG = "FragmentAccueil";
    private MainActivity acti;
    public Formulaire vueFormulaire;

    /*
    public FragmentAccueil(MainActivity acti){
        this.acti=acti;
    }
*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_accueil, container, false);
        RelativeLayout v = view.findViewById(R.id.tab1);

        vueFormulaire = new Formulaire(this.getActivity());
        vueFormulaire.refresh();
        v.addView(vueFormulaire);

        try {
            vueFormulaire.afficher();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}
