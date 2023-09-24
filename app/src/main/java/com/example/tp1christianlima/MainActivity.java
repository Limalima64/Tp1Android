package com.example.tp1christianlima;

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
                if(parent.getItemAtPosition(position).toString() == "3 Brasseurs"){
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

                tv_nombrePlace.setText("Choisisez un restaurant!");
                tv_nombrePlace.setTextColor(Color.BLACK);
            }
        });
    }

    public void onClickPageReservation(View view) {
        Intent reservActivity = new Intent(MainActivity.this, PageReservation.class);
        reservActivity.putExtra("leResto", restoChoisi);
        if(restoChoisi.getNomRestaurant() == "3 Brasseurs"){
            reservActivity.putExtra("laListeChoisi", reserv3Brasseur);
        }else{
            reservActivity.putExtra("laListeChoisi", reservElixor);
        }
        startActivityForResult(reservActivity, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Resources resources = getResources();

        if(requestCode == 1){
            if(restoChoisi.getNomRestaurant() == "3 Brasseurs"){
                reserv3Brasseur.addAll(data.getParcelableArrayListExtra("renvoieListe"));
                //probelme quand on cree deux reservation d'un coups et qu'on revien a la page principal, car ca calcul seulement le derniere item mis dans la liste et non l'avant dernier si on a cree 2 a la fois
                unResto1.setNbPlacesRestantes((unResto1.getNbPlacesRestantes()) - (reserv3Brasseur.get(reserv3Brasseur.size()-1).getNbPlace()));
            }else{
                reservElixor.addAll(data.getParcelableArrayListExtra("renvoieListe"));
                    unResto2.setNbPlacesRestantes((unResto2.getNbPlacesRestantes()) - (reservElixor.get(reservElixor.size()-1).getNbPlace()));
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
    }

    public void onClickPageToutReservations(View view) {
        Intent affichageLesReservsActivity = new Intent(MainActivity.this, PageAffichageReservations.class);
        affichageLesReservsActivity.putExtra("leResto", restoChoisi);
        if(restoChoisi.getNomRestaurant() == "3 Brasseurs"){
            affichageLesReservsActivity.putExtra("laListeChoisi", reserv3Brasseur);
        }else{
            affichageLesReservsActivity.putExtra("laListeChoisi", reservElixor);
        }
        startActivity(affichageLesReservsActivity);
    }
}