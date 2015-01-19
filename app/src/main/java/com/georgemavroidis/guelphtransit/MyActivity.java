package com.georgemavroidis.guelphtransit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import java.io.*;
import android.content.res.*;
import android.view.View;
import android.widget.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import android.app.*;
import android.graphics.drawable.*;
import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MyActivity extends Activity implements AdapterView.OnItemClickListener{
//    MapView mapview;
    private ArrayList<String> stop_names;
    private GoogleMap googleMap;
    ListView lv, mv;
    String currentClick;

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
//        AssetManager am = context.getAssets();
//        InputStream is = am.open("test.txt");

//            Toast.makeText(getApplicationContext(), "Hey George", 1).show();
        lv = (ListView) findViewById(R.id.listView);
        mv = (ListView) findViewById(R.id.mainList);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        ArrayList<String> transit_list = new ArrayList<String>();
        transit_list.add("1A");
        transit_list.add("1B");
        transit_list.add("2A");
        transit_list.add("2B");
        transit_list.add("3A");
        transit_list.add("3B");
        transit_list.add("4");
        transit_list.add("5");
        transit_list.add("6");
        transit_list.add("7");
        transit_list.add("8");
        transit_list.add("9");
        transit_list.add("10");
        transit_list.add("11");
        transit_list.add("12");
        transit_list.add("13");
        transit_list.add("14");
        transit_list.add("15");
        transit_list.add("16");
        transit_list.add("50");
        transit_list.add("56");
        transit_list.add("57");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, transit_list );

        lv.setAdapter(arrayAdapter);
        lv.setClickable(true);
        lv.setOnItemClickListener(this);
//        ActionBar actionBar = getActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));
        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // latitude and longitude
        double latitude = 43.541153;
        double longitude = -80.250296;

// create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps ");
// adding marker
//        googleMap.addMarker(marker);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latitude, longitude)).zoom(11).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);

    }
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getApplicationContext(), "You Clicked " +lv.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

            // Create a URL for the desired page
        TextView t = (TextView) findViewById(R.id.textView);
        t.setVisibility(View.GONE);
        try {
            URL url = new URL("http://www.georgemavroidis.com/uofg/transit/"+lv.getItemAtPosition(position).toString()+"_Week.csv");
            BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream()));

            stop_names = new ArrayList<String>();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {

//                String[] parts = inputLine.split(",");
                stop_names.add(inputLine);
//                System.out.println(inputLine);
            }
            System.out.println("SIZE = "+ stop_names.size());
            ArrayList<String> just_stop_names = new ArrayList<String>();
//            just_stop_names.add("test");
            for(int i = 1; i < stop_names.size(); i++){
                String[] splitted = stop_names.get(i).split(",");
                if(splitted.length != 0){
                    just_stop_names.add(splitted[0]);
                }
            }
            ArrayAdapter<String> stop_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, just_stop_names );
            mv.setAdapter(stop_adapter);
            mv.setClickable(true);
            currentClick = lv.getItemAtPosition(position).toString();
            mv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MyActivity.this.getApplicationContext(), second.class);
                    intent.putExtra("type", mv.getItemAtPosition(position).toString());
                    intent.putExtra("main", currentClick);
                    intent.putExtra("position", position);
                    startActivity(intent);


                }
            });;
            in.close();


        } catch(MalformedURLException er){
            er.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
