package com.example.a18may;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class PathTest {
    public static void PolyLineTest(GoogleMap googleMap, ArrayList<Vertex> vertices) {


        Polygon polygon;

        for (int i = 0; i+1 < vertices.size(); i++)
        {
            LatLng a = new LatLng(vertices.get(i).getLatitude(), vertices.get(i).getLongitude());
            Log.d("polylinetest", "vertice 1 added");
            LatLng b = new LatLng(vertices.get(i+1).getLatitude(), vertices.get(i+1).getLongitude());
            Log.d("polylinetest", "vertice 2 added");
            Log.d("PolyLineTest",a.toString()+ b.toString());
            polygon = googleMap.addPolygon(new PolygonOptions()
                    .add(a,b));
            polygon.setStrokeWidth(5f);

        }

    }
}
