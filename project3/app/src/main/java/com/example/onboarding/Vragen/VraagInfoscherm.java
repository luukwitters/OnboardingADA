package com.example.onboarding.Vragen;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.onboarding.Model.Code;
import com.example.onboarding.Promo.Promoscherm;
import com.example.onboarding.R;
import com.example.onboarding.helpers.VolleyHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class VraagInfoscherm extends AppCompatActivity {

    /**@author Walter Blaauw
     * @param txtInfo
     * @param txthead
     * @param imgFoto
     * @param Code
     */
    private TextView txtInfo;
    private TextView txthead;
    private ImageView imgFoto;
    Code code = new Code();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vraaginfoscherm);

        txthead = findViewById(R.id.txthead);
        Getpt("Vraag", txthead );
        txtInfo = findViewById(R.id.txtInfo);
        Getpt("InfoText", txtInfo );
        imgFoto = findViewById(R.id.imgFoto);
        Getpi("Image", imgFoto );
        // Aanmaken van het vraaginfoscherm en het aanspreken van de methodes die gebruikt moeten worden zodra deze activity opent
    }

    /**
     * wanneer er op de ingebouwde terugknop wordt gedrukt dan wordt je teruggestuurd naar het vraagscherm
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Vraagscherm.class);
        startActivity(intent);
        overridePendingTransition(R.anim.nothing, R.anim.slide_down);
    }

    /** Deze methode haalt de infotekst uit de JSON
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
                    //Setten van de gegevens die opgehaald worden uit de JSON
                    JSONObject jsonObject = new JSONObject (response.toString());

                    String infotext = jsonObject.getString("Vraag"+Code.VID);
                    JSONObject jsonObject1 = new JSONObject (infotext);

                    String infotext1 = jsonObject1.getString(finalItem);
                    finalId.setText(infotext1);

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


    /** Deze methode haalt de URL van de fotos die horen bij infotext uit de JSON
     * @param Value
     * @param id
     */
    public void Getpi(String Value, ImageView id) {
        String itemi = null;
        ImageView idi = null;

        idi = id;
        itemi = Value;

        final String finalItemi = itemi;
        final ImageView finalIdi = idi;
        // Aanspreken van de API om de gevraagde gegevens op te halen vanuit de JSON
        VolleyHelper secondHelper = new VolleyHelper(getBaseContext(), "https://adaonboarding.ml/t3/OnboardingAPI/GetTEXT");
        secondHelper.get("index.php", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    //Setten van de gegevens die opgehaald worden uit de JSON
                    JSONObject jsonObject = new JSONObject (response.toString());

                    String photoURL = jsonObject.getString("Vraag"+Code.VID);
                    JSONObject jsonObject1 = new JSONObject (photoURL);
                    String photoURL1 = jsonObject1.getString(finalItemi);

                    Picasso.get().load(photoURL1).resize(412, 161).into(finalIdi);

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
     * Deze methode wordt aangesproken wanneer er op de volgende knop gedrukt wordt
     * @param v
     */
    public void openVolgendeV(View v){
        //als VID lager dan iCount is wordt het VID opgehoogd met 1 en wordt er een nieuw vraagscherm getoond
        if (Code.VID < Code.iCount) {
            Code.VID++;
            code.SetVIDdb(getBaseContext(),code.getsid(),code.getvid());

            Intent intent = new Intent(this, Vraagscherm.class);
            startActivity(intent);
           overridePendingTransition(R.anim.nothing, R.anim.slide_down);
            // als VID gelijk is aan iCount wordt VID opgehoogd met 1 en wordt het volgende scherm getoond.
        } else if (Code.VID == Code.iCount){
            Code.VID++;
            code.SetVIDdb(getBaseContext(),code.getsid(),code.getvid());

            Intent intent = new Intent(this, Promoscherm.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}
