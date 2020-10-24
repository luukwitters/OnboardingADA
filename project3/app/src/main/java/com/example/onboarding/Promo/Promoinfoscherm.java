package com.example.onboarding.Promo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.onboarding.Introductie.IntoductieScherm;
import com.example.onboarding.Model.Code;
import com.example.onboarding.R;
import com.example.onboarding.helpers.VolleyHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Promoinfoscherm extends AppCompatActivity {
    //private com.example.onboarding.Model.PromoModel PromoModel;



        /**@author Joep Zegers
         * @param helper
         * @param btnTerug
         * @param btnmail
         * @param txtInfo
         * @param txthead
         * @param imgFoto
         * @param Code
         */
        private VolleyHelper helper;
        private Button btnTerug;
        private Button btnmail;
        private TextView txtInfo;
        private TextView txthead;
        private ImageView imgFoto;
        Code code = new Code();

        /**
         * @param savedInstanceState
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.promoinfoscherm);

                btnTerug = findViewById(R.id.btnTerug);
                //btnTerug = findViewById(R.id.btnTerug);
                btnmail = findViewById(R.id.btnmail);

                txthead = findViewById(R.id.txthead);
                Getpt("Vraag", txthead);
                txtInfo = findViewById(R.id.txtInfo);
                txtInfo.setMovementMethod(new ScrollingMovementMethod());
                Getpt("InfoText", txtInfo);
                imgFoto = findViewById(R.id.imgFoto);
                Getpi("Image", imgFoto);

                Animation mAnimation = new AlphaAnimation(1, 0);
                mAnimation.setDuration(800);
                mAnimation.setInterpolator(new LinearInterpolator());
                mAnimation.setRepeatCount(Animation.INFINITE);
                mAnimation.setRepeatMode(Animation.REVERSE);
                btnmail.startAnimation(mAnimation);


                //Button delay because nobody likes to read text but i'm a evil developer so they need to read the text
                btnTerug.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnTerug.setVisibility(View.VISIBLE);
                    }
                }, 1000 * 15);
            }


            /** send mail to
             * @param v
             */
            public void Openmail(View v){
                System.out.println(Code.VID + " is het VID nummer bij Promo Ja");
                try {
                    code.Setntw(getBaseContext(),code.getsid(),code.getvid(),"ja");
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setType("message/rfc822");
                    i.setData(Uri.parse("mailto: team03@adaonboarding.ml"));
                    //i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Aamelding PromoStudent");
                    i.putExtra(Intent.EXTRA_TEXT   , "Hierbij zou ik me graag willen aanmelden als promostudent");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(Promoinfoscherm.this, "er is geen Email app Geinstaleerd.", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    code.toast(getApplicationContext());
                }
            }


            /** go to next screen
             * @param v
             */
              public void NextScreen(View v){
            System.out.println(Code.VID + " is het VID nummer bij Promo Nee");
                  code.Setntw(getBaseContext(),code.getsid(),code.getvid(),"nee");
                  Code.VID = 100;
                  code.SetVIDdb(getBaseContext(),code.getsid(),code.getvid());
                  Intent intent = new Intent(this, IntoductieScherm.class);
                  startActivity(intent);
                  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
              }


            /**
             * We have our own back buttons so they don't need their own makes them more depended
             */
            @Override
            public void onBackPressed(){}

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


    /**
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

        VolleyHelper secondHelper = new VolleyHelper(getBaseContext(), "https://adaonboarding.ml/t3/OnboardingAPI/GetTEXT");
        secondHelper.get("index.php", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject jsonObject = new JSONObject (response.toString());

                    String promo = jsonObject.getString("Promo");
                    JSONObject jsonObject1 = new JSONObject (promo);
                    String promo1 = jsonObject1.getString(finalItemi);

                    Picasso.get().load(promo1).resize(412, 161).into(finalIdi);

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
