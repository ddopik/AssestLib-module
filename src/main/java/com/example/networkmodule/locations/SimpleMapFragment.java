package com.example.networkmodule.locations;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

 ;

import com.example.networkmodule.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by ddopi on 7/14/2017.
 */

public class SimpleMapFragment extends Fragment  {

    MapView mMapView;
    private GoogleMap googleMap;
    private GPSTrackerٍSingleton gpsTracker;
//    private WeatherModel weatherModel=new WeatherModel();
//    private ArrayList<CityWeather_Item> cityWeather_items;
private Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        unbinder= ButterKnife.bind(this,rootView);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

          this.loadMap();

        return rootView;
    }

    public void loadMap()
    {
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For dropping a marker at a point on the Map
//                cityWeather_items= weatherModel.getCityWeather_Items();
//                for(CityWeather_Item item:cityWeather_items)
//                {
//
//                    LatLng city_cordination = new LatLng(item.getCord_lat(),item.getCord_lon());
//                    googleMap.addMarker(new MarkerOptions().position(city_cordination).title(item.getCityName()).snippet(item.getTemp()+"C")).setTag(item);
//                }

                gpsTracker=new GPSTrackerٍSingleton(getActivity()){
                    @Override
                    public Fragment getFragmentContext() {
                        return SimpleMapFragment.this;
                    }

                    @Override
                    public GoogleMap getMap() {
                        return getGoogleMap();
                    }
                };
//                gpsTracker.getLocation();
//                Toast.makeText(getActivity(),"lat"+gpsTracker.getLatitude(),Toast.LENGTH_SHORT).show();

                // For showing a move to my location button
                gpsTracker.checkPermission(getActivity());
                googleMap.setMyLocationEnabled(true);

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
                {

                    @Override
                    public void onInfoWindowClick(Marker marker)
                    {
//                implement youtr action here
//                CityWeather_Item cityWeather_item=(CityWeather_Item) marker.getTag();
//                int id=cityWeather_item.getCity_id();
//                ((Activity)getActivity()).showWeatherDialog(id);

                    }

                });
                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (gpsTracker.checkPermission(getActivity())) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    public GoogleMap getGoogleMap()
    {
        return this.googleMap;
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



}

