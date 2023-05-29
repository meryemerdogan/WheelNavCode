package com.example.a18may;

import androidx.fragment.app.FragmentActivity;

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
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        drawTheRoad(testGraph,3,57);

    }
    public void drawTheRoad(Graph testGraph,int Vertex1ID, int Vertex2ID){
        ArrayList<Vertex> vertices = shortestPathBetween( testGraph,testGraph.getVertexByValue(Vertex1ID), testGraph.getVertexByValue(Vertex2ID));
        for(Vertex v: vertices){
            System.out.println(v.getID());
            Log.d("MainActivity nodes printed", "nodes to be printed");
        }

        PathTest.PolyLineTest(mMap,vertices);
    }
}