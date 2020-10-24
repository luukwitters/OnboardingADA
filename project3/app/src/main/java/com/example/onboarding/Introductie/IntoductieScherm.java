package com.example.onboarding.Introductie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.onboarding.Feedback.Feedback;
import com.example.onboarding.Model.Code;
import com.example.onboarding.Promo.Promoscherm;
import com.example.onboarding.R;
import com.example.onboarding.Vragen.Vraagscherm;

import com.example.onboarding.helpers.VolleyHelper;
import com.android.volley.Response;
import org.json.JSONException;
import org.json.JSONObject;


public class IntoductieScherm extends AppCompatActivity {

    /**@author Luuk Witters & Walter Blaauw & Joep Zegers
     * @param btnbegin
     * @param Context
     * @param Code
     */
    private VolleyHelper helper;
    private TextView txtIntroVraag;
    Code code = new Code();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intoductiescherm);

        txtIntroVraag = findViewById(R.id.txtIntroVraag);
        GetIT("Vraag", txtIntroVraag);

    }

    /**
     * @param v
     */
    public void openFeedback(View v)
    {
        Code.VID++;
        code.SetVIDdb(getBaseContext(),code.getsid(),code.getvid());
        Intent intent = new Intent(this, Feedback.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     *
     */
    @Override
    public void onBackPressed() {
        if (Code.VID == 100) {
            Integer iVergelijk = Code.iCount;
            Code.VID = iVergelijk + 1;
            System.out.println(Code.VID );
            code.SetVIDdb(getBaseContext(), code.getsid(), code.getvid());
            Intent i = new Intent(this, Promoscherm.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }


    /**
     * @param Value
     * @param idt
     */
    public void GetIT(String Value, TextView idt) {
        String item = null;
        TextView id = null;

        id = idt;
        item = Value;

        final String finalItem = item;
        final TextView finalId = id;

        VolleyHelper helper = new VolleyHelper(getBaseContext(), "https://adaonboarding.ml/t3/OnboardingAPI/GetTEXT");
        helper.get("index.php", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response)
            {

                try {

                    JSONObject jsonObject = new JSONObject(response.toString());

                    String introductie = jsonObject.getString("Introductie");
                    System.out.println(introductie);
                    JSONObject jsonObject1 = new JSONObject(introductie);

                    String intro1 = jsonObject1.getString(finalItem);
                    finalId.setText(intro1);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }//implements Response.Listener<JSONObject>, Response.ErrorListener



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                code.toast(getApplicationContext());
            }
        });
    }
}

