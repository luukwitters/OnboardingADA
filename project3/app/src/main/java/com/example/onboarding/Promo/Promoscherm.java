package com.example.onboarding.Promo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.onboarding.Beginscherm.Beginscherm;
import com.example.onboarding.Model.Code;
import com.example.onboarding.R;
import com.example.onboarding.Vragen.Vraagscherm;
import com.example.onboarding.helpers.VolleyHelper;

import org.json.JSONException;
import org.json.JSONObject;


public class Promoscherm extends AppCompatActivity {

    /**@author Joep Zegers
     * @param helper
     * @param Nothing
     * @param txtPromoVraag
     * @param btnPinfo
     * @param btnnothing
     * @param Code
     */
    private VolleyHelper helper;
    private Integer Nothing;
    private TextView txtPromoVraag;
    private Button btnPinfo;
    private Button btnnothing;
    Code code = new Code();


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promoscherm);
        btnPinfo = findViewById(R.id.btnPinfo);

        btnnothing = findViewById(R.id.btnnothing);
        Nothing = 0;

        txtPromoVraag = findViewById(R.id.txtPromoVraag);
        Getpt("Vraag", txtPromoVraag );

    }

    /** open info screen
     * @param v
     */
    public void openinfoPromo(View v){
        Intent intent = new Intent(this, Promoinfoscherm.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_up, R.anim.nothing);
    }


    /**
     * back press function
     */
    @Override
    public void onBackPressed() {
        if (Code.VID > Code.iCount) {
            Code.VID = Code.iCount;
            System.out.println(Code.VID);
            code.SetVIDdb(getBaseContext(), code.getsid(), code.getvid());
            Intent i = new Intent(this, Vraagscherm.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }


    /** Nothing to see here
     * @param v
     */
    public void Nothing(View v){
        if(Nothing == 7){
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.cust_toast_layout,
                    (ViewGroup)findViewById(R.id.relativeLayout1));
            Toast toast = new Toast(this);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.setView(view);
            toast.show();
            btnnothing.setEnabled(false);
        }else{Nothing++;}
    }


    /** Below this there are api functions they are there because android and java are both assholes and don't wanna listen . ps DON'T TOUCH THE FUNCTIONS THEY BITE
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

        VolleyHelper secondHelper = new VolleyHelper(getBaseContext(), "https://adaonboarding.ml/t3/OnboardingAPI/GetTEXT");
        secondHelper.get("index.php", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject jsonObject = new JSONObject (response.toString());

                    String promo = jsonObject.getString("Promo");
                    JSONObject jsonObject1 = new JSONObject (promo);

                    String promo1 = jsonObject1.getString(finalItem);
                    finalId.setText(promo1);

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
}
