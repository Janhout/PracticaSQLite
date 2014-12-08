package com.practicas.janhout.practicasqlite;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class EscuchadorTabs<T extends Fragment> implements ActionBar.TabListener {

    private Fragment fragment;
    private final String tag;

    public EscuchadorTabs(Activity activity, String tag, Fragment f) {
        this.tag = tag;
        fragment = f;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.replace(android.R.id.content, fragment, tag);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

}