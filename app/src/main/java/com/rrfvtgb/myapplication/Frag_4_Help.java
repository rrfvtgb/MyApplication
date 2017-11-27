package com.rrfvtgb.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.rrfvtgb.myapplication.AnimatedExpandableListView.AnimatedExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

public class Frag_4_Help extends Fragment {
    private static final String TAG = "Frag_4_Help";

    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_4__help, container, false);

//liste d'objets
        List<GroupItem> items = new ArrayList<GroupItem>();

//création des différents objets et ajout à la liste
        GroupItem home = new GroupItem();
        home.title = "Home";
        ChildItem homechild = new ChildItem();
        homechild.title = "L'onglet 'Home' vous permet de saisir vos informations";
        homechild.hint = "Aide pour home";
        home.items.add(homechild);
        items.add(home);

        GroupItem result = new GroupItem();
        result.title = "Result";
        ChildItem resultchild = new ChildItem();
        resultchild.title = "L'onglet 'Result' affiche les données selon le profil choisi";
        resultchild.hint = "Aide pour result";
        result.items.add(resultchild);
        items.add(result);

        GroupItem setup = new GroupItem();
        setup.title = "Setup";
        ChildItem setupchild = new ChildItem();
        setupchild.title = "L'onglet 'Setup' permet de sélectionner un profil";
        setupchild.hint = "Aide pour setup";
        setup.items.add(setupchild);
        items.add(setup);

        GroupItem help = new GroupItem();
        help.title = "Developed by";
        ChildItem helpchild = new ChildItem();
        helpchild.title = "Brulé Geoffrey; Gheysens Amaury";
        helpchild.hint = "Crédits";
        help.items.add(helpchild);
        items.add(help);
//affichage de la liste d'objet avec l'adapter
        adapter = new ExampleAdapter(getContext());
        adapter.setData(items);

        listView = view.findViewById(R.id.list);
        listView.setAdapter(adapter);

        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        listView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });

        return view;
    }
    /*
     * Preparing the list data
     */
    private static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String title;
        String hint;
    }

    private static class ChildHolder {
        TextView title;
        TextView hint;
    }

    private static class GroupHolder {
        TextView title;
    }

    /**
     * Adapter for our list of {@link GroupItem}s.
     */
    private class ExampleAdapter extends AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<GroupItem> items;

        public ExampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.frag_4_list_item, parent, false);
                holder.title = convertView.findViewById(R.id.text41);
                holder.hint = convertView.findViewById(R.id.text42);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            holder.title.setText(item.title);
            holder.hint.setText(item.hint);

            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.frag_4_list_group, parent, false);
                holder.title = convertView.findViewById(R.id.lblListHeader);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.title.setText(item.title);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }


    }
}
