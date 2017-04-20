package com.example.marion.jishinjohou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.marion.jishinjohou.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    ArrayList<Seisme> listeDeSeisme = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        //On receptionne ce que le SplashScreen nous renvoie
        Intent intent = getIntent();
        ArrayList listeReceptionnee = intent.getParcelableArrayListExtra("ListeSeisme");
        listeDeSeisme = listeReceptionnee;

        //On affiche dans une liste tout en la rendant cliquable pour plus de détail
        ListView listeSeismeAffiche = (ListView) findViewById(R.id.listView);
        ArrayAdapter<Seisme> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, listeDeSeisme);
        listeSeismeAffiche.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        listeSeismeAffiche.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Seisme seismeSelectionne = listeDeSeisme.get(position);

                        Intent intent = new Intent(MainActivity.this, Detail.class);
                        intent.putExtra("SeismeEnvoye", seismeSelectionne);
                        startActivity(intent);
                    }
                }
        );


    }


    //Permet de passer à la carte
    public void GoogleMaps(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("ListeSeisme", listeDeSeisme);
        startActivity(intent);
    }

}
