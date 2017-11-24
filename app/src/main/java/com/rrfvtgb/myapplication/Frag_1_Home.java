package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.json.JSONException;

public class Frag_1_Home extends Fragment {
    private static final String TAG = "Frag_1_Home";
    public Formulaire vueFormulaire;
    private MainActivity acti;

    /*
    public Frag_1_Home(MainActivity acti){
        this.acti=acti;
    }
*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_1_home, container, false);
        RelativeLayout v = view.findViewById(R.id.tab1);

        if(vueFormulaire == null) {
            vueFormulaire = new Formulaire(this.getContext());
            vueFormulaire.refresh();

            try {
                vueFormulaire.afficher();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            ((ViewGroup) vueFormulaire.getParent()).removeView(vueFormulaire);
        }

        v.addView(vueFormulaire);


        return view;
    }

    public String computeValue(){
        return vueFormulaire.computeValue();
    }
}
