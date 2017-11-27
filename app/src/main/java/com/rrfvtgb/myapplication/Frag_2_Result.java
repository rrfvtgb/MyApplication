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
    private static final String TAG = "Frag_2_Result";

    JSONArray table;
    MainActivity acti;
    ListView resultView;
    String data = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_2__result, container, false);

        resultView = view.findViewById(R.id.result_view);

        if (data != null)
            this.loadData(data);

        return view;
    }

    /**
     * récupère données profil
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;

        if (resultView != null) {
            loadData(data);
        }
    }

    /**parse le json et affiche les données profil
     *
     * @param data
     */
    public void loadData(String data) {
        //liste des données
        List<String> values = new ArrayList<>();
        try {
            //json parser selon les keys
            JSONObject jsonObject = new JSONObject(data);
            Iterator<?> iterator = jsonObject.keys();
            String result;
            //boucle sur chaque key du json
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                //récupération data
                result = key + ": " + jsonObject.getString(key);
                //ajout à la liste
                values.add(result);
            }
            //affichage des données
            ArrayAdapter adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.frag_2_listview, values);
            ListView listView = getActivity().findViewById(R.id.result_view);

            // Possible null
            if (listView != null) {
                listView.setAdapter(adapter);
            }
            //message informant user
            Toast.makeText(getActivity(), "Json Data has loaded", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void setTable(JSONObject j) {
        try {
            table = j.getJSONArray("service");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}