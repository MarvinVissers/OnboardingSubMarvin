package com.example.onboardingmarvin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.onboardingmarvin.helper.VolleyHelper;
import com.example.onboardingmarvin.modal.StudentModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    // Classes importeren
    private StudentModal mdlStudent;
    private VolleyHelper helper;

    // Lijst aanmaken voor studentgegevens
    private ArrayList<StudentModal> student;

    // Studentnummer aanmaken
    private int iStudentnummer;

    /**
     *
     * @param savedInstanceState Zorgt dat als de activity wordt aangeroepen dat er ook xml wordt aangesproken
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        /*
         * Feedback seekbar gradient achtergrond geven
         */
        // Instellen van het gradiant, 0 voor begin en 1 voor eind. X en Y voor de positie op de assen
        LinearGradient FeedbackSeekbarColors = new LinearGradient(0.f, 0.f, 900.f, 0.0f,
                // Kleuren aangeven
                new int[] { 0xFFFF0000, 0xFFFFFF00, 0xFF00FF00},
                // Manier van vullen aangeven
                null, Shader.TileMode.CLAMP);
        // Figuur maken
        ShapeDrawable feedbackSeekbar = new ShapeDrawable(new RectShape());
        // Kleuren aan het figuur meegeven
        feedbackSeekbar.getPaint().setShader(FeedbackSeekbarColors);

        // Seekbar ophalen en figuur meegeven
        SeekBar skFeedbackScore = findViewById(R.id.skScore);
        skFeedbackScore.setProgressDrawable(feedbackSeekbar);

        // Gegevens doorsturen naar database sturen
        helper = new VolleyHelper(getBaseContext(), "http://145.48.226.202:8000/bp3api");
        helper.get("GetStudent?studentnummer=" + iStudentnummer, null, this, this);

        System.out.println("http://145.48.226.202:8000/bp3api/GetStudent?studentnummer=" + iStudentnummer);
    }

    /**
     *
     * @param v View, is nodig om een button uit XML functionaliteit te geven. Haalt de layout op van de knop
     */
    // Functie voor het krijgen of de gegevens correct zijn
    public void opslaanGegevensCorrect(View v){
        try{
            // Radiobuttons ophalen
            RadioButton rbGegevensCorrectNee = findViewById(R.id.rbGegevensCorrectNee);
            RadioButton rbGegevensCorrectJa = findViewById(R.id.rbGegevensCorrectJa);

            // Studentnummer ophalen
            TextView tvStudentnummer = findViewById(R.id.tvStudentnummer);

            // Studentnummer tekst zetten in variabele
            iStudentnummer = Integer.parseInt(tvStudentnummer.getText().toString());

            // Kijken of de gegevens correct zijn of niet
            if(rbGegevensCorrectNee.isChecked()){
                // Gegevens doorsturen naar database sturen
                helper = new VolleyHelper(getBaseContext(), "http://145.48.226.202:8000/bp3api");
                helper.get("UpdateStudent?studentnummer=" + iStudentnummer + "&gegevens-correct=" + 0, null, this, this);

                System.out.println("http://192.168.2.6:8000/bp3api/UpdateStudent?studentnummer=" + iStudentnummer + "&gegevens-correct=" + 0);
            }
            // Else if voor als er iets fout gaat wordt het niet zomaar geupdated in de database
            else if(rbGegevensCorrectJa.isChecked()){
                // Gegevens doorsturen naar database sturen
                helper = new VolleyHelper(getBaseContext(), "http://145.48.226.202:8000/bp3api");
                helper.get("UpdateStudent?studentnummer=" + iStudentnummer + "&gegevens-correct=" + 1, null, this, this);

                System.out.println("http://192.168.2.6:8000/bp3api/UpdateStudent?studentnummer=" + iStudentnummer + "&gegevens-correct=" + 1);
            }
        }catch(Exception error) {
            System.out.println(error);
        }
    }

    /**
     *
     * @param v Zorgt dat als de activity wordt aangeroepen dat er ook xml wordt aangesproken
     */
    public void opslaanFeedback(View v){
        // Invoervelden ophalen
        SeekBar skFeedbackScore = findViewById(R.id.skScore);
        EditText txtFeedbackOpmerking = findViewById(R.id.txtFeedback);

        // Waarden uit invoervelden halen
        int iScore = skFeedbackScore.getProgress();
        String sOpmerking = txtFeedbackOpmerking.getText().toString();

        //System.out.println("Score is " + iScore + " en opmerking is " + sOpmerking);

        // Opmerking url friendly maken
        sOpmerking = sOpmerking.replaceAll(" ", "%20");

        helper = new VolleyHelper(getBaseContext(), "http://145.48.226.202:8000/bp3api");
        helper.post("SaveFeedback?score=" + iScore + "&opmerking=" + sOpmerking, null, this, this);

        System.out.println("http://192.168.2.6:8000/bp3api/SaveFeedback?score=" + iScore + "&opmerking=" + sOpmerking);
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
                    student.add(new StudentModal(array.getJSONObject(i)));
                }

                TextView tvStudentNaam = findViewById(R.id.tvStudentNaam);
                TextView tvStudentOpleiding = findViewById(R.id.tvStudentOpleiding);
                TextView tvStudentnummer = findViewById(R.id.tvStudentnummer);

                // Door de array lopen
                for (StudentModal StudentArray : student) {
                    // Teksvelden vullen met gegevens student
                    tvStudentNaam.setText(StudentArray.getStudentVoornaam() + " " + StudentArray.getStudentAchternaam());
                    tvStudentOpleiding.setText(StudentArray.getStudentOpleiding());
                    tvStudentnummer.setText(StudentArray.getStudentNummer());
                }
            }
        }catch (JSONException e){
            System.out.println(e);
        }
    }

}
