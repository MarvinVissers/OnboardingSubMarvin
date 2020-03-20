package com.example.onboardingmarvin.modal;

import org.json.JSONException;
import org.json.JSONObject;


public class StudentModal {

    int StudentNummer;
    int StudentGegevensCorrect;
    String StudentStartDatum;
    String StudentVoornaam, StudentAchternaam, StudentOpleiding;

    /**
     * @param Studentnummer Het studentnummer, Primary key in de database
     * @param voornaam De voornaam van de student
     * @param achternaam De achternaam van de student
     * @param opleiding De opleiding van de student, niet het id maar de naam
     * @param startdatum De startdatum van de student
     * @param GegevensCorrect Nummer om te kijken of gegevens correct zijn. 1 is correct, 0 is incorrect
     */
    public StudentModal(int Studentnummer, String voornaam, String achternaam, String opleiding, String startdatum, int GegevensCorrect){
        this.StudentNummer = Studentnummer;
        this.StudentVoornaam = voornaam;
        this.StudentAchternaam = achternaam;
        this.StudentOpleiding = opleiding;
        this.StudentStartDatum = startdatum;
        this.StudentGegevensCorrect = GegevensCorrect;
    }

    // Getters and setters

    /**
     * @return Geeft studentnummer terug
     */
    public int getStudentNummer() {
        return StudentNummer;
    }

    /**
     * @return Geeft student Gegevens correct terug
     */
    public int getStudentGegevensCorrect() {
        return StudentGegevensCorrect;
    }

    /**
     * @return Geeft student voornaam terug
     */
    public String getStudentVoornaam() {
        return StudentVoornaam;
    }

    /**
     * @return Geeft student achternaam terug
     */
    public String getStudentAchternaam() {
        return StudentAchternaam;
    }

    /**
     * @return Geeft opleiding terug, de naam van de opleiding en niet het id
     */
    public String getStudentOpleiding() {
        return StudentOpleiding;
    }

    /**
     * @return Geeft student startdatum terug
     */
    public String getStudentStartDaturm() {
        return StudentStartDatum;
    }

    /**
     * @param json Staat voor het JSONObject dat opgehaald wordt
     * @throws JSONException Een error in de json of het ophalen daarvan
     */
    public StudentModal(JSONObject json) throws JSONException {
        this.StudentNummer = json.getInt("studentnummer");
        this.StudentVoornaam = json.getString("student_voornaam");
        this.StudentAchternaam = json.getString("student_achternaam");
        // Opleiding naam wordt opgehaald, niet het id
        this.StudentOpleiding = json.getString("opleiding_naam");
        this.StudentStartDatum = json.getString("start_datum");
        // Kan leeg zijn, 2 betekent dan dat het nog niet ingevuld is
        this.StudentGegevensCorrect = json.optInt("gegevens_correct", 2);
    }

    /**
     * @return Geeft de string terug en niet het object als je het in een andere pagina ophaalt
     */
    @Override
    public String toString() {
        return "StudentModal{" +
                "StudentNummer=" + StudentNummer +
                ", StudentGegevensCorrect=" + StudentGegevensCorrect +
                ", StudentStartDatum='" + StudentStartDatum + '\'' +
                ", StudentVoornaam='" + StudentVoornaam + '\'' +
                ", StudentAchternaam='" + StudentAchternaam + '\'' +
                ", StudentOpleiding='" + StudentOpleiding + '\'' +
                '}';
    }
}
