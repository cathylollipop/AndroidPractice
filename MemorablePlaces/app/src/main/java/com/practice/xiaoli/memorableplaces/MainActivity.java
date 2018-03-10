package com.practice.xiaoli.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> places = new ArrayList<>();
    static ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView placeListView = (ListView)findViewById(R.id.placeListView);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.practice.xiaoli.memorableplaces", Context.MODE_PRIVATE);
        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longtitudes = new ArrayList<>();

        places.clear();
        locations.clear();
        latitudes.clear();
        longtitudes.clear();

        try {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes", ObjectSerializer.serialize(new ArrayList<String>())));
            longtitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longtitudes", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(places.size() > 0 && latitudes.size() > 0 && longtitudes.size() > 0){
            if(places.size() == latitudes.size() && latitudes.size() == longtitudes.size()){
                for(int i = 0; i < latitudes.size(); i++){
                    LatLng location = new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longtitudes.get(i)));
                    locations.add(location);
                }
            }
        }else{
            places.add("Add a new place...");
            locations.add(new LatLng(0, 0));
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);

        placeListView.setAdapter(arrayAdapter);

        placeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber", position);

                startActivity(intent);
            }
        });

    }
}
