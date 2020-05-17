package com.example.onboardingmarvin.helper;

/**
 *
 * @author Marvin Vissers
 */

public interface Feedback {
    // Functie voor het opslaan van de feedback over zijn gegevens
    void opslaanGegevensCorrect(int iGegevensCorrect);

    // Functie voor het opslaan van de feedback
    void opslaanFeedback(int iScore, String sOpmerking);
}
