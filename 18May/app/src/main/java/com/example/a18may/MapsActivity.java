package com.example.a18may;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.a18may.databinding.ActivityMapsBinding;

import static com.example.a18may.Dijkstra.addEdges;
import static com.example.a18may.Dijkstra.returnVerticiesToTheGraph;
import static com.example.a18may.Dijkstra.shortestPathBetween;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String from;
    private String to;

    private int fromID;
    private int toID;
    private ActivityMapsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent prevIntent = getIntent();
        from = prevIntent.getStringExtra("from");
        to = prevIntent.getStringExtra("to");

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng bilkent = new LatLng(39.86926298,32.74880535);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(bilkent, 17);
        mMap.addMarker(new MarkerOptions().position(bilkent).title("Marker in Bilkent"));
        mMap.moveCamera(cameraUpdate);
        googleMap.animateCamera(cameraUpdate);
        takeInput();
    }
    public String readTextFromAssets(Context context, String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager assetManager = context.getAssets();

        try {
            InputStream inputStream = assetManager.open(filename);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
    public void takeInput(){
        String tag = "MainActivity";

        String textNodes = readTextFromAssets(getApplicationContext(), "AllNodes.txt");

        Graph testGraph = new Graph(true, false);
        String textEdges = readTextFromAssets(getApplicationContext(),"AllEdges.txt");
        returnVerticiesToTheGraph(textNodes,testGraph);
        addEdges(textEdges, testGraph);

        nameToID(from,to);
        drawTheRoad(testGraph,fromID,toID);

    }
    public void drawTheRoad(Graph testGraph,int Vertex1ID, int Vertex2ID){
        ArrayList<Vertex> vertices = shortestPathBetween( testGraph,testGraph.getVertexByValue(Vertex1ID), testGraph.getVertexByValue(Vertex2ID));
        for(Vertex v: vertices){
            System.out.println(v.getID());
            Log.d("MainActivity nodes printed", "nodes to be printed");
        }

        PathTest.PolyLineTest(mMap,vertices);
    }
    private void nameToID(String from, String to)
    {
        if(from.equals("A"))
        {
            fromID = 57;
        }
        else if(from.equals("B"))
        {
           fromID = 127;
        }
        else if(from.equals("EA"))
        {
            fromID = 3;
        }else if(from.equals("EB"))
        {
            fromID = 118;
        }else if(from.equals("EE"))
        {
            fromID = 0;
        }else if(from.equals("FA"))
        {
            fromID = 85;
        }else if(from.equals("FB"))
        {
            fromID = 81;
        }else if(from.equals("FC"))
        {
            fromID = 81;
        }else if(from.equals("FF"))
        {
            fromID = 92;
        }else if(from.equals("G"))
        {
            fromID = 35;
        }else if(from.equals("H"))
        {
            fromID = 55;
        }else if(from.equals("L"))
        {
            fromID = 33;
        }else if(from.equals("MA"))
        {
            fromID = 59;
        }
        else if(from.equals("MASJID"))
        {
            fromID = 102;
        }else if(from.equals("METEKSAN MARKET"))
        {
            fromID = 130;
        }else if(from.equals("METEKSAN BOOKSTORE"))
        {
            fromID = 93;
        }else if(from.equals("SPEED"))
        {
            fromID = 94;
        }else if(from.equals("SA"))
        {
            fromID = 74;
        }else if(from.equals("SB"))
        {
            fromID = 72;
        }else if(from.equals("T"))
        {
            fromID = 130;
        }else if(from.equals("UNAM"))
        {
            fromID = 129;
        }else if(from.equals("V"))
        {
            fromID = 60;
        }

        if(to.equals("A"))
        {
            toID = 57;
        }
        else if(to.equals("B"))
        {
            toID = 127;
        }
        else if(to.equals("EA"))
        {
            toID = 3;
        }else if(to.equals("EB"))
        {
            toID = 118;
        }else if(to.equals("EE"))
        {
            toID = 0;
        }else if(to.equals("FA"))
        {
            toID = 85;
        }else if(to.equals("FB"))
        {
            toID = 81;
        }else if(to.equals("FC"))
        {
            toID = 81;
        }else if(to.equals("FF"))
        {
            toID = 92;
        }else if(to.equals("G"))
        {
            toID = 35;
        }else if(to.equals("H"))
        {
            toID = 55;
        }else if(to.equals("L"))
        {
            toID = 33;
        }else if(to.equals("MA"))
        {
            toID = 59;
        }
        else if(to.equals("MASJID"))
        {
            toID = 102;
        }else if(to.equals("METEKSAN MARKET"))
        {
            toID = 130;
        }else if(to.equals("METEKSAN BOOKSTORE"))
        {
            toID = 93;
        }else if(to.equals("SPEED"))
        {
            toID = 94;
        }else if(to.equals("SA"))
        {
            toID = 74;
        }else if(to.equals("SB"))
        {
            toID = 72;
        }else if(to.equals("T"))
        {
            toID = 130;
        }else if(to.equals("UNAM"))
        {
            toID = 129;
        }else if(to.equals("V"))
        {
            toID = 60;
        }
    }
}