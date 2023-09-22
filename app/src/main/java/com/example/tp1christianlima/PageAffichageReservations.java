package com.example.tp1christianlima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class PageAffichageReservations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_affichage_reservations);

        Intent intentfficher = getIntent();
        Restaurant leResto = intentfficher.getParcelableExtra("leResto");

        Toast.makeText(this, leResto.getNomRestaurant(), Toast.LENGTH_LONG).show();
    }
}