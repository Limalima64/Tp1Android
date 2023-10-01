package com.example.tp1christianlima;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class PageReservation extends AppCompatActivity {

    private SeekBar sb_places;
    private TextView tv_nombrePlaceReserv;
    private TextView tv_nomResto;
    private TextView tv_AffichageNombrePlaces;
    private int nombrePlaceReserv;
    private EditText et_dateReserv;
    private Spinner sp_heureDebut;
    private EditText et_heureFin;
    private EditText et_nom;
    private EditText et_telephone;

    ArrayList<Reservation> laListe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_reservation);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_nomResto = findViewById(R.id.tv_nomResto);
        tv_AffichageNombrePlaces = findViewById(R.id.tv_affichageNombrePlaces);
        sb_places = findViewById(R.id.sb_places);
        tv_nombrePlaceReserv = findViewById(R.id.tv_nombrePlaceReserv);
        Intent intentafficher = getIntent();
        Restaurant leResto = intentafficher.getParcelableExtra("leResto");

        Resources resources = getResources();

        tv_nomResto.setText(leResto.getNomRestaurant());

        // quand la page est créé on fait apparaiter le progres de la bare dans le text
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

        tv_nombrePlaceReserv.setText(sb_places.getProgress() + " " + resources.getString(R.string.uneOuAucunePlaceChoisiSp));
        // si l'utilisateur ne touche pas la bar alors on met la progression de base dans un variable, pour apres l'utiilser dans le click du bouton
        nombrePlaceReserv = sb_places.getProgress();

        // Faire en sorte que quand la bare augmente ou diminue on change le text
        sb_places.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progression, boolean fromUser) {
                if (progression > 1){
                    tv_nombrePlaceReserv.setText(progression + " " + resources.getString(R.string.plus1PlacesChoisiSp));
                }else{
                    tv_nombrePlaceReserv.setText(progression + " " + resources.getString(R.string.uneOuAucunePlaceChoisiSp));
                }
                //variable de la progression, pour l'utiliser quand on va reserver (bouton)
                nombrePlaceReserv = progression;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //trouver le id pour le edit text pour la date
        et_dateReserv = findViewById(R.id.et_dateReserv);

        et_dateReserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        //Spinner pour l'heure de reservation
        sp_heureDebut = findViewById(R.id.sp_heureDebut);
        et_heureFin = findViewById(R.id.et_heureFin);

        ArrayList<String> heuresReservations = new ArrayList<>();

        heuresReservations.add("16:00");
        heuresReservations.add("17:30");
        heuresReservations.add("19:00");
        heuresReservations.add("20:30");
        heuresReservations.add("22:00");

        ArrayAdapter<String> adapteurHeures = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, heuresReservations);
        adapteurHeures.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_heureDebut.setAdapter(adapteurHeures);

        sp_heureDebut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItemId() == 0){
                    et_heureFin.setText("17:29");
                }else if (parent.getSelectedItemId() == 1){
                    et_heureFin.setText("18:59");
                }else if (parent.getSelectedItemId() == 2){
                    et_heureFin.setText("20:29");
                }else if (parent.getSelectedItemId() == 3){
                    et_heureFin.setText("21:59");
                }else {
                    et_heureFin.setText("23:29");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Format du edit text du téléphone
        et_telephone = findViewById(R.id.et_telephone);

        et_telephone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String numTel = s.toString();
                numTel = numTel.replaceAll("[^0-9]", "");

                // Formatez le texte comme "999-999-9999"
                if (numTel.length() >= 10) {
                    numTel = numTel.substring(0, 10); // Limitez la longueur
                    numTel = numTel.substring(0, 3) + "-" + numTel.substring(3, 6) + "-" + numTel.substring(6, 10);
                }

                // Mettez à jour le texte de l'EditText
                et_telephone.removeTextChangedListener(this);
                et_telephone.setText(numTel);
                et_telephone.setSelection(numTel.length());
                et_telephone.addTextChangedListener(this);
            }
        });
    }

    // Faire apparaitre le calendrier
    private void showDatePicker() {
        Calendar calendrier = Calendar.getInstance();
        int annee = calendrier.get(Calendar.YEAR);
        int mois = calendrier.get(Calendar.MONTH);
        int jour = calendrier.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anneeChoisi, int moisChoisi, int jourChoisi) {
                String dateChoisi = jourChoisi +"/" + (moisChoisi+1) + "/" + anneeChoisi;
                et_dateReserv.setText(dateChoisi);
            }
        }, annee, mois, jour);

        datePicker.show();
    }

    public void onClickReservation(View view) {

        Intent intentafficher = getIntent();
        Restaurant leResto = intentafficher.getParcelableExtra("leResto");
        laListe = intentafficher.getParcelableArrayListExtra("laListeChoisi");
        Resources resources = getResources();

        et_nom = findViewById(R.id.et_nom);

        if(sb_places.getProgress() != 0) {
            //verifier si le nombre de places choisi entre dans le nombre de places restantes
            if (sb_places.getProgress() <= leResto.getNbPlacesRestantes()) {
                //verifier si la date est valide
                if (et_dateReserv.getText().toString().matches("\\d{1}/\\d{1}/\\d{4}") || et_dateReserv.getText().toString().matches("\\d{2}/\\d{1}/\\d{4}") || et_dateReserv.getText().toString().matches("\\d{1}/\\d{2}/\\d{4}") || et_dateReserv.getText().toString().matches("\\d{2}/\\d{2}/\\d{4}")) {
                    //verifier si le nom est valide
                    if (!et_nom.getText().toString().equals("")) {
                        //vérifier le numero de telephone
                        if (et_telephone.getText().toString().matches("\\d{3}-\\d{3}-\\d{4}")) {
                            //Tout est valide alors on creer une reservation pour le restaurant choisi et on modifie la valeur du restaurant et on renvoie le restaurant a l'acceuil
                            laListe.add(new Reservation(laListe.size() + 1, et_dateReserv.getText().toString(), nombrePlaceReserv, sp_heureDebut.getSelectedItem().toString(), et_heureFin.getText().toString(), et_nom.getText().toString(), et_telephone.getText().toString()));
                            Intent renvoyerLaListe = new Intent();
                            renvoyerLaListe.putExtra("renvoieListe", laListe);
                            setResult(10, renvoyerLaListe);
                            Toast.makeText(this,  resources.getString(R.string.ToastReservNom) + " " + et_nom.getText() + " " + resources.getString(R.string.ToastReservSauve), Toast.LENGTH_SHORT).show();
                            Log.v("Information réservation", "Le numero de reservation: " + laListe.get(laListe.size() - 1).getNoReservation() + ", le nombre de place: " + laListe.get(laListe.size() - 1).getNbPlace() + ", la date: " + laListe.get(laListe.size() - 1).getDateReservation().toString() + ", l'heure du debut: " + laListe.get(laListe.size() - 1).getBlocReservationDebut());
                            sb_places.setProgress(0);
                            et_dateReserv.setText("");
                            et_nom.setText("");
                            et_telephone.setText("");
                            sp_heureDebut.setSelection(0);
                            leResto.setNbPlacesRestantes((leResto.getNbPlacesRestantes()) - (laListe.get(laListe.size() - 1).getNbPlace()));
                            if (leResto.getNbPlacesRestantes() <= 4) {
                                if (leResto.getNbPlacesRestantes() == 0 || leResto.getNbPlacesRestantes() == 1) {
                                    tv_AffichageNombrePlaces.setText(leResto.getNbPlacesRestantes() + " " + resources.getString(R.string.uneOuAucunePlace));

                                } else {
                                    tv_AffichageNombrePlaces.setText(leResto.getNbPlacesRestantes() + " " + resources.getString(R.string.plus1Places));
                                }
                                tv_AffichageNombrePlaces.setTextColor(Color.RED);
                            } else {
                                tv_AffichageNombrePlaces.setText(leResto.getNbPlacesRestantes() + " " + resources.getString(R.string.plus1Places));
                                tv_AffichageNombrePlaces.setTextColor(Color.BLUE);
                            }
                            //Enlever le clavier ICI !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        } else {
                            Toast.makeText(this, resources.getString(R.string.ToastErreurNum), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, resources.getString(R.string.ToastErreurNom), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, resources.getString(R.string.ToastErreurDate), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, resources.getString(R.string.ToastErreurPasPlace), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, resources.getString(R.string.ToastErreurPlaceChoisi), Toast.LENGTH_SHORT).show();
        }
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