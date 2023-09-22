package com.example.tp1christianlima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class PageReservation extends AppCompatActivity {

    private SeekBar sb_places;
    private TextView tv_nombrePlaceReserv;
    private TextView tv_nomResto;
    private TextView tv_AffichageNombrePlaces;
    private int nombrePlaceReserv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_reservation);

        Intent intentfficher = getIntent();
        Restaurant leResto = intentfficher.getParcelableExtra("leResto");

        tv_nomResto = findViewById(R.id.tv_nomResto);
        tv_AffichageNombrePlaces = findViewById(R.id.tv_affichageNombrePlaces);
        sb_places = findViewById(R.id.sb_places);
        tv_nombrePlaceReserv = findViewById(R.id.tv_nombrePlaceReserv);

        Resources resources = getResources();

        tv_nomResto.setText(leResto.getNomRestaurant());

        if(leResto.getNbPlacesRestantes() <= 4){
            if (leResto.getNbPlacesRestantes() == 0 || leResto.getNbPlacesRestantes() == 1){
                tv_AffichageNombrePlaces.setText(leResto.getNbPlacesRestantes() + " " + resources.getString(R.string.uneOuAucunePlace));

            }else{
                tv_AffichageNombrePlaces.setText(leResto.getNbPlacesRestantes() + " " + resources.getString(R.string.plus1Places));
            }
            tv_AffichageNombrePlaces.setTextColor(Color.RED);
        }else{
            tv_AffichageNombrePlaces.setText(leResto.getNbPlacesRestantes() + " " + resources.getString(R.string.plus1Places));
            tv_AffichageNombrePlaces.setTextColor(Color.BLUE);
        }

        tv_nombrePlaceReserv.setText(sb_places.getProgress() + " place réservée");
        nombrePlaceReserv = sb_places.getProgress();

        sb_places.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progression, boolean fromUser) {
                if (progression > 1){
                    tv_nombrePlaceReserv.setText(progression + " places réservées");
                }else{
                    tv_nombrePlaceReserv.setText(progression + " place réservée");
                }
                nombrePlaceReserv = progression;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void onClickDateChoisi(View view) {

    }
}