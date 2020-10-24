package com.example.onboarding.Eindscherm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.onboarding.Feedback.Feedback;
import com.example.onboarding.Introductie.IntoductieScherm;
import com.example.onboarding.Model.Code;
import com.example.onboarding.R;
import com.example.onboarding.helpers.VolleyHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class Eindscherm extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    /**@author Luuk Witters
     * @param btnEind
     * @param helper
     * @param sStudent
     * @param Code
     */
    private Button btnEind;
    private VolleyHelper helper;
    private String sStudent;
    Code code = new Code();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eindscherm);
        sStudent = code.getsid();

        btnEind = (Button) findViewById(R.id.btnEind);

    }

    /**
     * @param v
     */
    public void eindApp(View v)
    {
        Toast.makeText(this, "Eind Onboarding", Toast.LENGTH_LONG).show();

        helper = new VolleyHelper(getBaseContext(), "https://adaonboarding.ml/t3/OnboardingAPI");
        helper.get("SetEDT/index.php?SID=" + sStudent, null, this, this);
    }

    /**
     *
     */
    @Override
    public void onBackPressed() {
        if (Code.VID == 102) {
            Code.VID--;
            System.out.println(Code.VID );
            code.SetVIDdb(getBaseContext(), code.getsid(), code.getvid());
            Intent i = new Intent(this, Feedback.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    /**
     * @param error Als er een error is met het ophalen van json
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        code.toast(getApplicationContext());
    }

    /**
     * @param response Wat er gebeurt als er json teruggegeven wordt uit de api
     */
    @Override
    public void onResponse(JSONObject response) {
        System.out.println(response.toString());
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
