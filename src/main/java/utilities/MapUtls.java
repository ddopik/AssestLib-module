package utilities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.example.networkmodule.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ddopik..@_@
 */
public class MapUtls {

    private final static int CAMERA_ZOOM = 16;

    public static boolean isLocationPermissionGranted(Context context) {
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED)
            return false;
        return true;
    }

    public static LatLng getMyCurrentLocation(Location mLastLocation, GoogleApiClient mGoogleApiClient, Context context) {
        LatLng latLng = null;
        //This if statement is written by android itself
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return latLng;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation == null && MapUtls.isLocationPermissionGranted(context)) {
            buildAlertMessageNoGps(context);
        } else {
            //mha moveMapCamera( new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), R.drawable.ic_test,"","",map );
            //VOO moveMapCamera(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), map);
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }

        return latLng;
    }

    public static void buildAlertMessageNoGps(final Context baseActivity) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(baseActivity);
        builder
                .setMessage(baseActivity.getResources().getString(R.string.map_fragment_open_gps))
                .setCancelable(false)
                .setPositiveButton(baseActivity.getResources().getString(R.string.map_fragment_yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    @SuppressWarnings("unused") final DialogInterface dialog,
                                    @SuppressWarnings("unused") final int id) {
                                //mha mLocationEnabled = true;
                                baseActivity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                .setNegativeButton(baseActivity.getResources().getString(R.string.map_fragment_no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        Log.e("MapUtls","Error--->UnSpecified userLocation");
                    }
                });
        AlertDialog alert;
        alert = builder.create();
        alert.show();
    }

    public static void moveMapCameraBounded(LatLng[] latLngs, GoogleMap map, Context context) {
        if (latLngs == null || latLngs.length == 0)
            return;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : latLngs)
            builder.include(latLng);

        LatLngBounds bounds = builder.build();
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 175));
    }


    //----------------New configuration------begin---------

    public static void moveMapCamera(LatLng latLng, GoogleMap map, Context context) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM), 1500, null);
    }

    public static void moveMapCameraWithoutAnimation(LatLng latLng, GoogleMap map, Context context) {
        if (map == null)
            return;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM));
    }

    public static Marker addMarkerToMap(LatLng latLng, int iconId, GoogleMap map, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        int markerWidth = (int) Utilities.convertDpToPx(context.getResources().getDimension(R.dimen.map_location_marker_icon_width) / density);
        int markerHeight = (int) Utilities.convertDpToPx(context.getResources().getDimension(R.dimen.map_location_marker_icon_height) / density);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(iconId, markerWidth, markerHeight, context)));

        return map.addMarker(markerOptions);
    }

    public static Bitmap resizeMapIcons(int iconId, int width, int height, Context context) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(), iconId);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    public static Marker addDriverMarkerToMap(LatLng latLng, int iconId, GoogleMap map, Context context) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(iconId));
        return map.addMarker(markerOptions);
    }

    // TODO: 12/2/2017  this method animate marker smootly and should be integrated with addDriverMarkerToMap() to achieve this logic
    public static void animateMarker(final GoogleMap mGoogleMapObject, final Marker marker, final LatLng toPosition,
                                     final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mGoogleMapObject.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;
        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    // Post again 10ms later.
                    handler.postDelayed(this, 10);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    //----------------New configuration------end---------

    //---------------To get estimated time between two locations-----begin-----

    public static long getEstimatedTimeBetweenLocations(String jsonText) {
        try {
            final JSONObject json = new JSONObject(jsonText);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routesObject = routeArray.getJSONObject(0);
            JSONArray legsArray = routesObject.getJSONArray("legs");
            JSONObject legsObject = legsArray.getJSONObject(0);
            JSONObject durationObject = legsObject.getJSONObject("duration");
            long estimatedTime = durationObject.getLong("value");
            return estimatedTime;

        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //---------------To get estimated time between two locations-----end-----


    public static String convertLatLngToDegreeFormat(double latitude, double longitude) {
        StringBuilder builder = new StringBuilder();

        String latitudeDegrees = Location.convert(Math.abs(latitude), Location.FORMAT_SECONDS);
        String[] latitudeSplit = latitudeDegrees.split(":");
        builder.append(latitudeSplit[0]);
        builder.append("°");
        builder.append(latitudeSplit[1]);
        builder.append("'");
        builder.append(latitudeSplit[2]);
        builder.append("\"");

        if (latitude < 0) {
            builder.append("S ");
        } else {
            builder.append("N ");
        }
        builder.append(" ");

        String longitudeDegrees = Location.convert(Math.abs(longitude), Location.FORMAT_SECONDS);
        String[] longitudeSplit = longitudeDegrees.split(":");
        builder.append(longitudeSplit[0]);
        builder.append("°");
        builder.append(longitudeSplit[1]);
        builder.append("'");
        builder.append(longitudeSplit[2]);
        builder.append("\"");

        if (longitude < 0) {
            builder.append("W ");
        } else {
            builder.append("E ");
        }

        return builder.toString();
    }


    //---------------To get estimated distance between two locations on Google maps-----begin-----

    public static double getEstimatedDistanceBetweenLocations(String jsonText) {
        try {
            final JSONObject json = new JSONObject(jsonText);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routesObject = routeArray.getJSONObject(0);
            JSONArray legsArray = routesObject.getJSONArray("legs");
            JSONObject legsObject = legsArray.getJSONObject(0);
            JSONObject durationObject = legsObject.getJSONObject("distance");
            double estimatedDistance = durationObject.getDouble("value");
            return estimatedDistance;

        } catch (JSONException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    //---------------To get estimated distance between two locations on Google maps-----end-----

    //---------------To draw line on map-----begin-----

    public static Polyline drawPath(String result, GoogleMap mMap) {

        Polyline line = null;

        try {
            //Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePolygon(encodedString);
            line = mMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(5)
                    .color(Color.parseColor("#DC6F27"))//Google maps blue color
                    .geodesic(true)
            );

           /*
           for(int z = 0; z<list.size()-1;z++){
                LatLng src= list.get(z);
                LatLng dest= list.get(z+1);
                Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude,   dest.longitude))
                .width(2)
                .color(Color.BLUE).geodesic(true));
            }
           */
        } catch (JSONException e) {

        }

        return line;
    }

    private static List<LatLng> decodePolygon(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    //---------------To draw line on map-----end-----
}
