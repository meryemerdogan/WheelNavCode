package com.example.a18may;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SelectRoadsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String from;
    String to;
    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;
    Button showRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_roads);
        showRoute = findViewById(R.id.button);

        Spinner spinner_1 = findViewById(R.id.spinner1);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.buildingNames, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_1.setAdapter(adapter1);
        spinner_1.setOnItemSelectedListener(this);

        Spinner spinner_2 = findViewById(R.id.spinner2);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.buildingNames, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_2.setAdapter(adapter2);
        spinner_2.setOnItemSelectedListener(this);

        showRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("from", from );
                i.putExtra("to", to );

                startActivity(i);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getAdapter() == adapter1)
        {
            String text = parent.getItemAtPosition(position).toString();
            from = text;
        }
        else{
            String text = parent.getItemAtPosition(position).toString();
            to = text;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}