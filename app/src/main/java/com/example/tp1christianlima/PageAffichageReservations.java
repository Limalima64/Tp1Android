package com.example.tp1christianlima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

public class PageAffichageReservations extends AppCompatActivity {

    TextView tv_titreResto;

    Spinner sp_dates;

    private ListView lv_reservation;

    private adapterReservation adapteurReservation;

    ArrayList<Reservation> reservParDate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_affichage_reservations);

        Resources resources = getResources();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentafficher = getIntent();
        Restaurant leResto = intentafficher.getParcelableExtra("leResto");
        ArrayList<Reservation> laListe = intentafficher.getParcelableArrayListExtra("laListeChoisi");

        tv_titreResto = findViewById(R.id.tv_titreResto);
        tv_titreResto.setText(leResto.getNomRestaurant());

        sp_dates = findViewById(R.id.sp_dates);

        ArrayList<String> listDates = new ArrayList<>();
        for(int i = 0; i < laListe.size(); i++){
            listDates.add(laListe.get(i).getDateReservation());
        }

        Collections.sort(listDates, new Comparator<String>() {
            @Override
            public int compare(String date1, String date2) {
                SimpleDateFormat dateFormat;
                if (date1.contains("/") && date2.contains("/")) {
                    String[] parts1 = date1.split("/");
                    String[] parts2 = date2.split("/");

                    if (parts1.length == 3 && parts2.length == 3) {
                        // Déterminez le format (dd/mm/yyyy, d/mm/yyyy, d/m/yyyy, dd/m/yyyy)
                        if (parts1[0].length() == 1) {
                            dateFormat = new SimpleDateFormat("d/M/yyyy");
                        } else {
                            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        }

                        try {
                            Date parsedDate1 = dateFormat.parse(date1);
                            Date parsedDate2 = dateFormat.parse(date2);
                            return parsedDate1.compareTo(parsedDate2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return 0; // En cas d'erreur de parsing, les dates sont considérées égales.
                        }
                    }
                }
                return 0;
            }
        });

        if(listDates.isEmpty()){
            listDates.add(resources.getString(R.string.ErreurAucuneDate3Page));
        }

        for(int i = 0; i < listDates.size(); i++){
            for(int j = i+1; j < listDates.size(); j++){
                if(listDates.get(i).equals(listDates.get(j))){
                    listDates.remove(j);
                    j--;
                }
            }
        }

        ArrayAdapter<String> adapteur = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listDates);
        adapteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dates.setAdapter(adapteur);

        //ListView

        lv_reservation = findViewById(R.id.lv_reservation);

        sp_dates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                reservParDate.clear();

                //Enregistrer dans le tableau en croissant a partir de la date du debut de la reservation
                for(Reservation reserv: laListe){
                    if(reserv.getDateReservation().equals(parent.getSelectedItem())){
                        reservParDate.add(reserv);
                    }
                }

                Collections.sort(reservParDate, new Comparator<Reservation>() {
                    @Override
                    public int compare(Reservation reserv1, Reservation reserv2) {

                        return reserv1.getBlocReservationDebut().compareTo(reserv2.getBlocReservationDebut());
                    }
                });

                adapteurReservation = new adapterReservation(getApplicationContext(), reservParDate);
                lv_reservation.setAdapter(adapteurReservation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lv_reservation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reservation reservationChoisi = reservParDate.get(position);

                Toast.makeText(getApplicationContext(), resources.getString(R.string.ToastPage3NumReserv) + " " + reservationChoisi.getNoReservation() + " & " + resources.getString(R.string.ToastPage3NumTel) + " " + reservationChoisi.getTelPersonne(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}