package com.example.cpe436.rpgme.controller;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cpe436.rpgme.R;

/**
 * Default fragment
 */
public class MyFragment extends Fragment {
    public String name;
    protected Activity parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Bundle args = getArguments();
        int id = args.getInt("id", R.layout.list);
        name = args.getString("name", "");
        parent = getActivity();

        //
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_character_info);
        if (fragment != null) {
            transaction.remove(fragmentManager.findFragmentById(R.id.content_character_info));
            transaction.commit();
        }

        return inflater.inflate(id, container, false);
    }
}