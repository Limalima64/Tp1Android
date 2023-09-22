package com.example.tp1christianlima;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

public class Reservation implements Parcelable {
    private int noReservation;
    private String dateReservation;
    private int nbPlace;
    private String blocReservationDebut;
    private String blocReservationFin;
    private String nomPersonne;
    private String telPersonne;

    protected Reservation(Parcel in) {
        noReservation = in.readInt();
        dateReservation = in.readString();
        nbPlace = in.readInt();
        blocReservationDebut = in.readString();
        blocReservationFin = in.readString();
        nomPersonne = in.readString();
        telPersonne = in.readString();
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    public int getNoReservation() {
        return noReservation;
    }

    public void setNoReservation(int noReservation) {
        this.noReservation = noReservation;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public String getBlocReservationDebut() {
        return blocReservationDebut;
    }

    public void setBlocReservationDebut(String blocReservationDebut) {
        this.blocReservationDebut = blocReservationDebut;
    }

    public String getGetBlocReservationFin() {
        return blocReservationFin;
    }

    public void setGetBlocReservationFin(String getBlocReservationFin) {
        this.blocReservationFin = getBlocReservationFin;
    }

    public String getNomPersonne() {
        return nomPersonne;
    }

    public void setNomPersonne(String nomPersonne) {
        this.nomPersonne = nomPersonne;
    }

    public String getTelPersonne() {
        return telPersonne;
    }

    public void setTelPersonne(String telPersonne) {
        this.telPersonne = telPersonne;
    }

    public Reservation(int noReservation, String dateReservation, int nbPlace, String blocReservationDebut, String blocReservationFin, String nomPersonne, String telPersonne) {
        this.noReservation = noReservation;
        this.dateReservation = dateReservation;
        this.nbPlace = nbPlace;
        this.blocReservationDebut = blocReservationDebut;
        this.blocReservationFin = blocReservationFin;
        this.nomPersonne = nomPersonne;
        this.telPersonne = telPersonne;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(noReservation);
        dest.writeString(dateReservation);
        dest.writeInt(nbPlace);
        dest.writeString(blocReservationDebut);
        dest.writeString(blocReservationFin);
        dest.writeString(nomPersonne);
        dest.writeString(telPersonne);
    }
}
