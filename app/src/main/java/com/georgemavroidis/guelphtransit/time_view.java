package com.georgemavroidis.guelphtransit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class time_view extends android.support.v4.app.Fragment {

    public static time_view newInstance(String bus, int someInt, int position) {
        time_view myFragment = new time_view();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        args.putString("bus", bus);
        args.putInt("pos", position);
        myFragment.setArguments(args);

        return myFragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_time_view, container, false);
        int pageInt =  getArguments().getInt("someInt");
        String bussed = getArguments().getString("bus");
        int pos = getArguments().getInt("pos");
//        System.out.println("position = "+pos);
        ListView lv, mv;
        lv = (ListView)rootView.findViewById(R.id.timeList);
        String time = "Week";
        if(pageInt == 1) time = "Saturday";
        if(pageInt == 2) time = "Sunday";

        try {
            URL url = new URL("http://www.georgemavroidis.com/uofg/transit/"+bussed+"_"+time+".csv");
            BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream()));

            ArrayList<String> list = new ArrayList<String>();
            String inputLine;

            ArrayList<String> just_stop_names = new ArrayList<String>();

            while ((inputLine = in.readLine()) != null) {
                just_stop_names.add(inputLine);
            }
            ArrayList<String> wittle = new ArrayList<String>();
//            just_stop_names.add("test");
            String[] splitted = just_stop_names.get(pos+1).split(",");
            if(splitted.length != 0){
                for(int i = 1; i < splitted.length; i++){
                    wittle.add(splitted[i]);
                }
            }

//
            ArrayAdapter<String> stop_adapter;
            stop_adapter = new ArrayAdapter<String>(inflater.getContext(), R.layout.bus_times, wittle );
            lv.setAdapter(stop_adapter);
            in.close();


        } catch(MalformedURLException er){
            er.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rootView;
    }

}