package com.example.onboarding.Vragen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.onboarding.Beginscherm.Beginscherm;
import com.example.onboarding.Model.Code;
import com.example.onboarding.Promo.Promoscherm;
import com.example.onboarding.R;
import com.example.onboarding.helpers.VolleyHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class Vraagscherm extends AppCompatActivity {

    /**@author Walter Blaauw
     * @param sAntwoord
     * @param Code
     */
    public String sAntwoord;
    Code code = new Code();

// Aanmaken van het vraagscherm en het aanspreken van de methodes die gebruikt moeten worden zodra deze activity opent
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vraagscherm);
        TextView txtVraag = findViewById(R.id.txtVraag);
        TextView txtVraagNummer = findViewById(R.id.txtVraagNmr);

        txtVraagNummer.setText("Vraag "+ code.VID);
        Getpt("Vraag", txtVraag);


    }
    /** Deze methode haalt de vraag en het antwoord uit de JSON
     * @param Value
     * @param idt
     */
    public void Getpt(String Value, TextView idt) {
        String item = null;
        TextView id = null;

        id = idt;
        item = Value;

        final String finalItem = item;
        final TextView finalId = id;
        // Aanspreken van de API om de gevraagde gegevens op te halen vanuit de JSON
        VolleyHelper secondHelper = new VolleyHelper(getBaseContext(), "https://adaonboarding.ml/t3/OnboardingAPI/GetTEXT");
        secondHelper.get("index.php", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = new JSONObject (response.toString());
                    String sVraag = null, sVraag1;
                    // als de VID lager of gelijk aan iCount is worden de gegevens opgehaald uit de JSON
                    if (Code.VID <= Code.iCount) {
                    sVraag = jsonObject.getString("Vraag"+Code.VID);
                    JSONObject jsonObject1 = new JSONObject (sVraag);
                    JSONObject jsonObject2 = new JSONObject (sVraag);

                    sVraag1 = jsonObject1.getString(finalItem);
                    finalId.setText(sVraag1);
                    sAntwoord = jsonObject2.getString("Antwoord");
                    }
                    // als de voorwaarden van de ifStatement niet voldaan worden wordt de VID+1 en opent het Promoscherm
                    else {
                        Code.VID++;
                        Intent i = new Intent(Vraagscherm.this, Promoscherm.class);
                        startActivity(i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                code.toast(getApplicationContext());
            }
        });


    }

    /**
     * Deze methode wordt aangesproken nadat er op de jaKnop gedrukt wordt
      * @param v
     */
    public void openJa(View v) {
        code.Setntw(getBaseContext(),code.getsid(),code.getvid(),"ja");
        System.out.println(sAntwoord + " is het ingevoerde antwoord");
        //als het antwoord gelijk is aan het antwoord dat wordt ophaald uit de JSON opent het vraagscherm
        if (sAntwoord.equals("ja")) {
            Intent intent = new Intent(this, VraagInfoscherm.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up, R.anim.nothing);
            //als het antwoord niet gelijk is
        } else if (sAntwoord.equals("nee")) {
            //als vid kleiner dan iCount is wordt er een nieuwe vraag opgehaald
            if (Code.VID < Code.iCount) {
                Code.VID++;
                code.SetVIDdb(getBaseContext(),code.getsid(),code.getvid());

                System.out.println(Code.VID + " is vid na Ja IF");
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(getIntent());
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //als vid gelijk is aan iCount wordt het volgende scherm geopend
            } else if (Code.VID == Code.iCount){
                Code.VID++;
                code.SetVIDdb(getBaseContext(),code.getsid(),code.getvid());

                System.out.println(Code.VID + " is vid na Ja");
                Intent intent = new Intent(this, Promoscherm.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    }

    /**
     * Deze methode wordt aangesproken nadat er op de NeeKnop gedrukt wordt
      * @param v
     */
    public void openNee(View v) {
        code.Setntw(getBaseContext(),code.getsid(),code.getvid(),"nee");
        System.out.println(sAntwoord + " is het ingevoerde antwoord");
        //als het antwoord gelijk is aan het antwoord dat wordt ophaald uit de JSON opent het vraagscherm
        if (sAntwoord.equals("nee")) {
            Intent intent = new Intent(this, VraagInfoscherm.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up, R.anim.nothing);
            //als het antwoord niet gelijk is
        } else if (sAntwoord.equals("ja")) {
            //als vid kleiner dan iCount is wordt er een nieuwe vraag opgehaald
            if (Code.VID < Code.iCount) {
                Code.VID++;
                code.SetVIDdb(getBaseContext(),code.getsid(),code.getvid());

                System.out.println(Code.VID + " is vid na Nee IF");
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(getIntent());
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //als vid gelijk is aan iCount wordt het volgende scherm geopend
            } else if (Code.VID == Code.iCount){
                Code.VID++;
                code.SetVIDdb(getBaseContext(),code.getsid(),code.getvid());

                System.out.println(Code.VID + " is vid na Nee");
                Intent intent = new Intent(this, Promoscherm.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    }

    /**
     *  Deze methode wordt aangesproken wanneer de ingebouwde terugknop wordt gebruikt
     */
    @Override
    public void onBackPressed(){
        //als code VID gelijk is aan 1 word je teruggestuurd naar het beginscherm
        if (Code.VID == 1){
            Intent i = new Intent(Vraagscherm.this, Beginscherm.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        //Als code vid hoger is dan 1 wordt er 1 vanafgehaald en opent de vorige vraag
        else{
            Code.VID--;
            code.SetVIDdb(getBaseContext(),code.getsid(),code.getvid());
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            startActivity(getIntent());
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}