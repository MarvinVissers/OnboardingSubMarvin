package com.example.on_boarding.controller;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.on_boarding.helper.VolleyHelper;
import com.example.on_boarding.modal.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class studentController implements Response.ErrorListener, Response.Listener<JSONObject> {

    // Classes importeren
    private VolleyHelper helper;
    private Context ctActivity;

    private ArrayList<Student> lijst;
    private TextView tvStudentTekst;

    private int iStudentnummer;
    private int iScherm;

    /**
     * @param ctActivity Context van het scherm, is nodig om de api te benaderen
     * @param iStudentnummer Het studentnummer van de student
     */
    public studentController(Context ctActivity, TextView tvStudentTekst, int iScherm, int iStudentnummer){
        this.ctActivity = ctActivity;
        this.helper = new VolleyHelper(ctActivity, "https://adaonboarding.ml/t4");
        this.tvStudentTekst = tvStudentTekst;
        this.iScherm = iScherm;
        this.iStudentnummer = iStudentnummer;
        this.lijst = new ArrayList();
    }

    /**
     * Student ophalen uit de api
     */
    public void getStudent(){
        // Gegevens doorsturen naar database sturen
        helper.get("GetStudent?studentnummer=" + iStudentnummer, null, this, this);
    }

    /**
     * @param iGegevensCorrect 1 of 0. 1 staat ervoor dat de student zijn of haar gegevens correct zijn en 0 dat de gegevens van de student incorrect zijn.
     */
    public void opslaanGegevensCorrect(int iGegevensCorrect) {
        // Gegevens doorsturen naar database sturen
        helper.post("UpdateStudent?studentnummer=" + iStudentnummer + "&gegevens-correct=" + iGegevensCorrect, null, null, this);
    }

    /**
     * @param iScore De score van de feedback die is gegeven. 1 tot 100
     * @param sOpmerking De opmerking die bij de feeedback is gegevens. Kan leeg zijn.
     */
    public void opslaanFeedback(int iScore, String sOpmerking) {
        // Gegevens doorsturen naar database sturen
        helper.post("SaveFeedback?score=" + iScore + "&opmerking=" + sOpmerking, null, null, this);
    }

    /**
     *
     * @param error Als er een error is met het ophalen van json
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println(error);
    }

    /**
     *
     * @param response Wat er gebeurt als er json teruggegeven wordt uit de api
     */
    @Override
    public void onResponse(JSONObject response) {
        try {
            // JSONArray ophalen
            JSONArray array = response.getJSONArray("item");

            // Kijken of deze leeg is, zodat er geen error onstaat bij geven feedback of gegevens correct
            if(array != null && array.length() > 0 ) {
                for (int i = 0; i < array.length(); i++) {
                    lijst.add(new Student(array.getJSONObject(i)));
                }

                // Door de array lopen
                for (Student StudentArray : lijst) {
                    switch(iScherm){
                        case 1: tvStudentTekst.setText("Welkom " + StudentArray.getStudentVoornaam());
                            break;
                        case 5: tvStudentTekst.setText(StudentArray.getStudentVoornaam() + " " + StudentArray.getStudentAchternaam() + "\n" + StudentArray.getStudentOpleiding());
                            break;
                        case 6: tvStudentTekst.setText("Gefeliciteerd " + StudentArray.getStudentVoornaam() + " " + StudentArray.getStudentAchternaam() + "! Je hebt bij deze je onboarding afgerond! Je opleiding start op " + StudentArray.getStudentStartDaturm() + "! Tot dan!");
                            break;
                    }
                }
            }
        }catch (JSONException error){
            System.out.println(error);
        }
    }
}
