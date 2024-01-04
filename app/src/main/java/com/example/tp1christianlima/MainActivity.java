package com.example.tp1christianlima;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Restaurant unResto1;
    private Restaurant unResto2;

    Spinner sp_restaurants;

    TextView tv_nombrePlace;

    private Restaurant restoChoisi;

    ArrayList<Reservation> reserv3Brasseur = new ArrayList<>();
    ArrayList<Reservation> reservElixor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unResto1 = new Restaurant(1, "3 Brasseurs", 30, 30);
        unResto2 = new Restaurant(2, "Elixor", 16, 16);

        sp_restaurants = findViewById(R.id.sp_restaurants);

        ArrayList<String> listRestaurants = new ArrayList<>();

        listRestaurants.add(unResto1.getNomRestaurant());
        listRestaurants.add(unResto2.getNomRestaurant());



        ArrayAdapter<String> adapteur = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listRestaurants);
        adapteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_restaurants.setAdapter(adapteur);

        sp_restaurants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_nombrePlace = findViewById(R.id.tv_nombrePlace);
                Resources resources = getResources();
                if("3 Brasseurs".equals(parent.getItemAtPosition(position).toString())){
                    restoChoisi = unResto1;
                }else{
                    restoChoisi = unResto2;
                }

                if(restoChoisi.getNbPlacesRestantes() <= 4){
                    if (restoChoisi.getNbPlacesRestantes() == 0 || restoChoisi.getNbPlacesRestantes() == 1){
                        tv_nombrePlace.setText(restoChoisi.getNbPlacesRestantes() + " " + resources.getString(R.string.uneOuAucunePlace));

                    }else{
                        tv_nombrePlace.setText(restoChoisi.getNbPlacesRestantes() + " " + resources.getString(R.string.plus1Places));
                    }
                    tv_nombrePlace.setTextColor(Color.RED);
                }else{
                    tv_nombrePlace.setText(restoChoisi.getNbPlacesRestantes() + " " + resources.getString(R.string.plus1Places));
                    tv_nombrePlace.setTextColor(Color.BLUE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tv_nombrePlace = findViewById(R.id.tv_nombrePlace);
                Resources resources = getResources();

                tv_nombrePlace.setText("Coihsisez un restaurant!");
                tv_nombrePlace.setTextColor(Color.BLACK);
            }
        });
    }

    public void onClickPageReservation(View view) {
        Intent reservActivity = new Intent(MainActivity.this, PageReservation.class);
        reservActivity.putExtra("leResto", restoChoisi);
        if(restoChoisi.getNomRestaurant() == "3 Brasseurs"){
            reservActivity.putParcelableArrayListExtra("laListeChoisi", reserv3Brasseur);
        }else{
            reservActivity.putParcelableArrayListExtra("laListeChoisi", reservElixor);
        }
        startActivityForResult(reservActivity, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Resources resources = getResources();
        if (data != null) {
            if (requestCode == 10) {
               if (restoChoisi.getNomRestaurant().equals("3 Brasseurs")) {
                    reserv3Brasseur.clear();
                    unResto1.setNbPlacesRestantes(unResto1.getNbPlacesMax());
                    reserv3Brasseur.addAll(data.getParcelableArrayListExtra("renvoieListe"));
                    for (int i = 0; i < reserv3Brasseur.size(); i++) {
                        unResto1.setNbPlacesRestantes(unResto1.getNbPlacesRestantes() - reserv3Brasseur.get(i).getNbPlace());
                    }
                } else {
                    reservElixor.clear();
                    unResto2.setNbPlacesRestantes(unResto2.getNbPlacesMax());
                    reservElixor.addAll(data.getParcelableArrayListExtra("renvoieListe"));
                    for (int i = 0; i < reservElixor.size(); i++) {
                        unResto2.setNbPlacesRestantes(unResto2.getNbPlacesRestantes() - reservElixor.get(i).getNbPlace());
                    }
                }

                if (restoChoisi.getNbPlacesRestantes() <= 4) {
                    if (restoChoisi.getNbPlacesRestantes() == 0 || restoChoisi.getNbPlacesRestantes() == 1) {
                        tv_nombrePlace.setText(restoChoisi.getNbPlacesRestantes() + " " + resources.getString(R.string.uneOuAucunePlace));

                    } else {
                        tv_nombrePlace.setText(restoChoisi.getNbPlacesRestantes() + " " + resources.getString(R.string.plus1Places));
                    }
                    tv_nombrePlace.setTextColor(Color.RED);
                } else {
                    tv_nombrePlace.setText(restoChoisi.getNbPlacesRestantes() + " " + resources.getString(R.string.plus1Places));
                    tv_nombrePlace.setTextColor(Color.BLUE);
                }
            }
        }
    }

    public void onClickPageToutReservations(View view) {
        Intent affichageLesReservsActivity = new Intent(MainActivity.this, PageAffichageReservations.class);
        affichageLesReservsActivity.putExtra("leResto", restoChoisi);
        if(restoChoisi.getNomRestaurant() == "3 Brasseurs"){
            affichageLesReservsActivity.putParcelableArrayListExtra("laListeChoisi", reserv3Brasseur);
        }else{
            affichageLesReservsActivity.putParcelableArrayListExtra("laListeChoisi", reservElixor);
        }
        startActivity(affichageLesReservsActivity);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("restoBrasseur", unResto1);
        outState.putParcelable("restoElixor", unResto2);
        outState.putParcelableArrayList("listeBrasseur", reserv3Brasseur);
        outState.putParcelableArrayList("listeElixor", reservElixor);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        unResto1 = savedInstanceState.getParcelable("restoBrasseur");
        unResto2 = savedInstanceState.getParcelable("restoElixor");
        reserv3Brasseur = savedInstanceState.getParcelableArrayList("listeBrasseur");
        reservElixor = savedInstanceState.getParcelableArrayList("listeElixor");

        unResto1.setNbPlacesRestantes(unResto1.getNbPlacesMax());
        for (int i = 0; i < reserv3Brasseur.size(); i++) {
            unResto1.setNbPlacesRestantes(unResto1.getNbPlacesRestantes() - reserv3Brasseur.get(i).getNbPlace());
        }
        unResto2.setNbPlacesRestantes(unResto2.getNbPlacesMax());
        for (int i = 0; i < reservElixor.size(); i++) {
            unResto2.setNbPlacesRestantes(unResto2.getNbPlacesRestantes() - reservElixor.get(i).getNbPlace());
        }
    }
}