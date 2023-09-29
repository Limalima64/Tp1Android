package com.example.tp1christianlima;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class adapterReservation extends BaseAdapter {

    private Context dContext;

    private List<Reservation> reservationListe = new ArrayList<>();

    private TextView tv_nomReservation, tv_infoReservation;

    @Override
    public int getCount() {
        return reservationListe.size();
    }

    @Override
    public Reservation getItem(int position) {
        return reservationListe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public adapterReservation(Context _Context, ArrayList<Reservation> _liste) {
        this.dContext = _Context;
        this.reservationListe = _liste;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = LayoutInflater.from(dContext).inflate(R.layout.liste_element_reservation, parent, false);
        tv_nomReservation = view.findViewById(R.id.tv_nomReservation);
        tv_nomReservation.setText(this.getItem(position).getNomPersonne());

        tv_infoReservation = view.findViewById(R.id.tv_infoReservation);
        if(this.getItem(position).getNbPlace() > 1){
            tv_infoReservation.setText(this.getItem(position).getNbPlace() + " places / " + this.getItem(position).getBlocReservationDebut() + " - " + this.getItem(position).getBlocReservationFin());
        }else{
            tv_infoReservation.setText(this.getItem(position).getNbPlace() + " place / " + this.getItem(position).getBlocReservationDebut() + " - " + this.getItem(position).getBlocReservationFin());
        }

        return view;
    }
}
