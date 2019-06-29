package app.being.pattadonjaisin.beingwellness;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.graphics.Color.green;
import static android.graphics.Color.rgb;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pagerdash extends Fragment {
    private String pcal = "0";
    private String pSug = "0";
    private String pSod = "0";
    private String pFat = "0";
    String status = null;
    Bundle bundle;
    public Pagerdash() {
        // Required empty public constructors
    }


    public Pagerdash newInstance(String Cal, String Sug, String Sod, String Fat, String Status){
        Pagerdash cal = new Pagerdash();
        Bundle savecal = new Bundle();
        savecal.putString("Cal", Cal);
        savecal.putString("Sug", Sug);
        savecal.putString("Sod", Sod);
        savecal.putString("Fat", Fat);
        savecal.putString("Status", Status);
        cal.setArguments(savecal);
        return cal;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_pagerdash, container, false);

        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null) {

//            pcal = getArguments().getString("Cal", "0");
//            pSug = getArguments().getString("Sug", "0");
//            pSod = getArguments().getString("Sod", "0");
//            pFat = getArguments().getString("Fat", "0");
//            status = getArguments().getString("Status");
        }



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager =  view.findViewById(R.id.viewpager) ;
        TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setBackgroundColor(rgb(83, 188  , 83));
        tabLayout.setTabTextColors(rgb(245, 246  , 250),rgb(245, 246, 250));
        //tabLayout.addTab(tabLayout.newTab().setText("Hiya"));
        tabLayout.setSelectedTabIndicatorColor(rgb(255, 159, 243));
        pageradapter pageradapter = new pageradapter(getChildFragmentManager(),3,bundle);


        viewPager.setAdapter(pageradapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
