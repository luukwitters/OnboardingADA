package com.example.onboarding.Feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.onboarding.Eindscherm.Eindscherm;
import com.example.onboarding.Introductie.IntoductieScherm;
import com.example.onboarding.Model.Code;
import com.example.onboarding.R;
import com.example.onboarding.Vragen.Vraagscherm;
import com.example.onboarding.helpers.VolleyHelper;
import org.json.JSONException;
import org.json.JSONObject;


public class Feedback extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

        private Button btnIntakeEens;
        private Button btnIntakeOneens;
        private Button btnOpenEens;
        private Button btnOpenOneens;
        private Button btnNext;
        private TextView txtFeedback;
        private TextView txtIntake;
        private TextView txtOpenDag;
        private TextView txtOpenVraag;
        private int iTeller = 0;
        Boolean bIntakeEens = Boolean.FALSE;
        Boolean bIntakeOneens = Boolean.FALSE;
        Boolean bOpenEens = Boolean.FALSE;
        Boolean bOpenOneens = Boolean.FALSE;
        Code code = new Code();

        private VolleyHelper helper;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.feedback);

            btnIntakeEens = findViewById(R.id.btnIntakeEens);
            btnIntakeOneens = findViewById(R.id.btnIntakeOneens);
            btnOpenEens = findViewById(R.id.btnOpenEens);
            btnOpenOneens = findViewById(R.id.btnOpenOneens);
            btnNext = findViewById(R.id.btnNext);
            txtFeedback = findViewById(R.id.txtFeedback);
            txtIntake = findViewById(R.id.txtIntake);
            txtOpenDag = findViewById(R.id.txtOpendag);
            txtOpenVraag = findViewById(R.id.txtOpenVraag);

            txtFeedback.addTextChangedListener(feedbackTextWatcher);

        }

        private TextWatcher feedbackTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String feedbackInput = txtFeedback.getText().toString().trim();

                btnNext.setEnabled(!feedbackInput.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

    @Override
    public void onBackPressed() {
        if (Code.VID == 101) {
            Code.VID--;
            System.out.println(Code.VID );
            code.SetVIDdb(getBaseContext(), code.getsid(), code.getvid());
            Intent i = new Intent(this, IntoductieScherm.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }


        public void IntakeEens(View v) {
            System.out.println("Eens Intake");
            btnIntakeEens.setVisibility(View.INVISIBLE);
            btnIntakeOneens.setVisibility(View.INVISIBLE);
            btnOpenEens.setVisibility(View.VISIBLE);
            btnOpenOneens.setVisibility(View.VISIBLE);
            txtIntake.setVisibility(View.INVISIBLE);
            txtOpenDag.setVisibility(View.VISIBLE);

        }

        public void IntakeOneens(View v) {
            System.out.println("Oneens Intake");
            btnIntakeEens.setVisibility(View.INVISIBLE);
            btnIntakeOneens.setVisibility(View.INVISIBLE);
            btnOpenEens.setVisibility(View.VISIBLE);
            btnOpenOneens.setVisibility(View.VISIBLE);
            txtIntake.setVisibility(View.INVISIBLE);
            txtOpenDag.setVisibility(View.VISIBLE);

        }

        public void OpenEens(View v) {
            System.out.println("Eens Open");
            txtOpenVraag.setVisibility(View.VISIBLE);
            txtFeedback.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnOpenEens.setVisibility(View.INVISIBLE);
            btnOpenOneens.setVisibility(View.INVISIBLE);
            txtOpenDag.setVisibility(View.INVISIBLE);

        }

        public void OpenOneens(View v) {
            System.out.println("Oneens Open");
            txtOpenVraag.setVisibility(View.VISIBLE);
            txtFeedback.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnOpenEens.setVisibility(View.INVISIBLE);
            btnOpenOneens.setVisibility(View.INVISIBLE);
            txtOpenDag.setVisibility(View.INVISIBLE);
        }

        public void Verder(View v) {
            TextView tvFeedback = findViewById(R.id.txtFeedback);
            String txtFeedback = tvFeedback.getText().toString();

            String sIntake = "Oneens";
            String sOpenDag = "Oneens";

            String sStudent = "test";

            if (bIntakeEens == true) {
                sIntake = "Eens";
            }

            if (bOpenEens == true) {
                sOpenDag = "Eens";
            }


            helper = new VolleyHelper(getBaseContext(), "https://adaonboarding.ml/t3/OnboardingAPI");
            helper.get("SetFB/indexVis.php?SID=" + sStudent + "&mrk1=" + sIntake + "&mrk2=" + sOpenDag + "&fdb=" + txtFeedback, null, this, this);

            Code.VID++;
            code.SetVIDdb(getBaseContext(),code.getsid(),code.getvid());
            Intent intent = new Intent(this, Eindscherm.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            System.out.println("Button works");
        }

        /**
         * @param error Als er een error is met het ophalen van json
         */
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println(error);
            //Tosti
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





