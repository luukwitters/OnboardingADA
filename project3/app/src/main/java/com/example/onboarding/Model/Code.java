package com.example.onboarding.Model;


import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.onboarding.Promo.Promoscherm;
import com.example.onboarding.Vragen.Vraagscherm;
import com.example.onboarding.helpers.VolleyHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Code extends AppCompatActivity {

    /**@author Walter Blaauw & Joep Zegers
     * @param Random create instance of Random class
     * @param VID
     * @param iCount
     * @param StudentID
     */
    Random rand = new Random();
    public static int VID;
    public static int iCount;
    public static String StudentID;


    /** set StudentID
     * @param sid
     */
    public void setsid(String sid){ StudentID = sid;}

    /** get sid
     * @return
     */
    public String getsid(){ return StudentID; }

    /** get vid
     * @return
     */
    public Integer getvid(){ return VID; }


    /** this is the toast message that shows when a error is called
     * @param context
     */
    public void toast(Context context){ String[] message = {"WAT DOE JE NU??!!", "Dit is duidelijk niet onze fout", "Tosti ham kaas", "Ja shit, nu is alles kapot. Goed gedaan.", "sigh"};
        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(5);
        Toast.makeText(context, message[rand_int1],Toast.LENGTH_LONG).show();
    }

    /** Deze methode haalt het vID op uit de database
     * @param context
     */
    public void getVIDdb(Context context){

        Context Context = null;
        Context = context;
        final Context finalContext = Context;
        // Aanspreken van de API en zo de SQL-query uit te voeren
        VolleyHelper secondHelper = new VolleyHelper(finalContext, "https://adaonboarding.ml/t3/OnboardingAPI");
        secondHelper.get("GetVID/index.php?SID=" + StudentID , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    Integer result = Integer.parseInt(jsonObject.getString("result"));
                    //VID wordt Het resultaat van de query. Hierdier is VID te gebruiken in de applicatie als waarde
                    VID = result;

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Locale error handlin
            }
        });
    }


    /** Deze methode haalt de totale hoeveelheid vragen uit de JSON via de API
     * @param context
     */
    public void getCount(Context context) {

        Context Context = null;
        Context = context;
        final Context finalContext = Context;

        // Aanspreken van de API en zo de SQL-query uit te voeren
        VolleyHelper secondHelper = new VolleyHelper(finalContext, "https://adaonboarding.ml/t3/OnboardingAPI/GetTEXT");
        secondHelper.get("index.php", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = new JSONObject (response.toString());
                    String sVraag = null, sVraag1;
                    Integer teller;

                    sVraag = jsonObject.getString("Count");
                    JSONObject jsonObject1 = new JSONObject (sVraag);
                    teller = jsonObject1.getInt("count");
                    //iCount wordt Het resultaat van de query. Hierdier is iCount te gebruiken in de applicatie als waarde
                    iCount = teller;
                    System.out.println(Code.iCount + " is de teller");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Locale error handlin
            }
        });


    }


    /** set the anwser
     * @param context
     * @param sid
     * @param vid
     * @param ntw
     */
    public void Setntw(Context context, String sid, Integer vid, String ntw) {
        Context Context = null;
        String SID = null;
        Integer VID = null;
        String NTW = null;

        Context = context;
        SID = sid;
        VID = vid;
        NTW = ntw;


        final Context finalContext = Context;
        final String finalSID = SID;
        final Integer finalVID = VID;
        final String finalNTW = NTW;

        VolleyHelper secondHelper = new VolleyHelper(finalContext, "https://adaonboarding.ml/t3/OnboardingAPI");
        secondHelper.get("SetNTW/index.php?SID=" + finalSID + "&VID=" + finalVID + "&NTW=" + finalNTW , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Locale error handlin
            }
        });
    }

    /** Deze methode zet het VID in de database via de API
     * @param context
     * @param sid
     * @param vid
     */
    public void SetVIDdb(Context context, String sid, Integer vid) {
        Context Context = null;
        String SID = null;
        Integer VID = null;

        Context = context;
        SID = sid;
        VID = vid;

        final Context finalContext = Context;
        final String finalSID = SID;
        final Integer finalVID = VID;
        // Aanspreken van de API en zo de SQL-query uit te voeren
        VolleyHelper secondHelper = new VolleyHelper(finalContext, "https://adaonboarding.ml/t3/OnboardingAPI");
        secondHelper.get("SetVID/?SID=" + finalSID + "&VID=" + finalVID , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Locale error handlin
            }
        });
    }

}