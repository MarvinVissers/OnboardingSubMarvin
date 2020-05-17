import android.content.Context;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.onboardingmarvin.helper.Feedback;
import com.example.onboardingmarvin.helper.VolleyHelper;
import com.example.onboardingmarvin.modal.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class feedbackController implements Response.ErrorListener, Feedback {

    // Classes importeren
    private VolleyHelper helper;
    private Context ctActivity;

    private int iStudentnummer;

    /**
     * @param ctActivity Context van het scherm, is nodig om de api te benaderen
     * @param iStudentnummer Het studentnummer van de student
     */

    public feedbackController(Context ctActivity, int iStudentnummer){
        this.ctActivity = ctActivity;
        this.helper = new VolleyHelper(ctActivity, "https://adaonboarding.ml/t4");
        this.iStudentnummer = iStudentnummer;
        this.lijst = new ArrayList();
    }

    /**
     * @param iGegevensCorrect 1 of 0. 1 staat ervoor dat de student zijn of haar gegevens correct zijn en 0 dat de gegevens van de student incorrect zijn.
     */
    @Override
    public void opslaanGegevensCorrect(int iGegevensCorrect) {
        // Gegevens doorsturen naar database sturen
        helper.post("UpdateStudent?studentnummer=" + iStudentnummer + "&gegevens-correct=" + iGegevensCorrect, null, null, this);
    }

    /**
     * @param iScore De score van de feedback die is gegeven. 1 tot 100
     * @param sOpmerking De opmerking die bij de feeedback is gegevens. Kan leeg zijn.
     */
    @Override
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
}