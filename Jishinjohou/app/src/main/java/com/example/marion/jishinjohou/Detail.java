package com.example.marion.jishinjohou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {
    Seisme seisme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        TextView titleTV = (TextView) findViewById(R.id.titre);
        TextView placeTV = (TextView) findViewById(R.id.place);
        TextView codeTV = (TextView) findViewById(R.id.code);
        TextView dateTV = (TextView) findViewById(R.id.date);
        TextView detailTV = (TextView) findViewById(R.id.detail);
        TextView linkTV = (TextView) findViewById(R.id.link);
        TextView longTV = (TextView) findViewById(R.id.longitude);
        TextView latTV = (TextView) findViewById(R.id.latitude);
        Button carte = (Button) findViewById(R.id.buttonCarte);

        seisme = (Seisme) intent.getSerializableExtra("SeismeEnvoye");


        titleTV.setText(seisme.getTitle());
        placeTV.setText("Place : " + seisme.getPlace());
        codeTV.setText("Code : " + seisme.getCode());
        dateTV.setText("Date : " + seisme.getDate());
        detailTV.setText("Detail :");
        linkTV.setText(seisme.getDetail());
        longTV.setText("Longitude : " + seisme.getLongitude());
        latTV.setText("Latitude : " + seisme.getLatitude());


    }

    //Permet de passer à la carte
    public void GoogleMaps(View view) {

        ArrayList<Seisme> ListeSeisme = new ArrayList<>();
        ListeSeisme.add(seisme);
        Intent intent = new Intent(Detail.this, MapsActivity.class);
        intent.putExtra("ListeSeisme", ListeSeisme);
        startActivity(intent);
    }
}
