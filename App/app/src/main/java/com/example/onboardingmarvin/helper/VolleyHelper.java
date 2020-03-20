package com.example.onboardingmarvin.helper;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class VolleyHelper {


    private final RequestQueue requestQueue;
    private final ImageLoader imageLoader;
    private final String baseUrl;

    /**
     * @param c Staat voor de activity waarop json wordt aangesproken
     * @param baseUrl Staat voor de basis van de website van json
     */
    public VolleyHelper(Context c, String baseUrl) {
        this.requestQueue = Volley.newRequestQueue(c);
        this.baseUrl = baseUrl;
        this.imageLoader = new ImageLoader(requestQueue, new BitmapLruCache());
    }

    /**
     * @param method Staat voor wat je wilt doen in de api: GET, POST, PUT eb DELETE
     * @return Geeft de basis URL terug met een /
     */
    private String contructUrl(String method){
        return baseUrl + "/" + method;
    }

    /**
     * @return Geeft een imageLoader terug
     */
    public ImageLoader getImageLoader(){
        return imageLoader;
    }

    /**
     * @param method GET, iets uit de database halen
     * @param jsonRequest de request die we sturen
     * @param listener luisterd naar de uitkomst
     * @param errorListener kijkt of er een error uitkomt
     */
    public void get(String method, JSONObject jsonRequest,
                    Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.GET, contructUrl(method), jsonRequest, listener, errorListener);
        requestQueue.add(objRequest);
    }

    /**
     * @param method PUT, iets in de database aanpassen
     * @param jsonRequest de request die we sturen
     * @param listener luisterd naar de uitkomst
     * @param errorListener kijkt of er een error uitkomt
     */
    public void put(String method, JSONObject jsonRequest,
                    Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.PUT, contructUrl(method), jsonRequest, listener, errorListener);
        requestQueue.add(objRequest);
    }

    /**
     * @param method POST, naar de database toesturen
     * @param jsonRequest de request die we sturen
     * @param listener luisterd naar de uitkomst
     * @param errorListener kijkt of er een error uitkomt
     */
    public void post(String method, JSONObject jsonRequest,
                     Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, contructUrl(method), jsonRequest, listener, errorListener);
        requestQueue.add(objRequest);

//        objRequest.setRetryPolicy(new DefaultRetryPolicy(9000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        objRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 2;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

    /**
     * @param method DELETE, iets uit de database halen
     * @param jsonRequest de request die we sturen
     * @param listener luisterd naar de uitkomst
     * @param errorListener kijkt of er een error uitkomt
     */
    public void delete(String method, JSONObject jsonRequest,
                       Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.DELETE, contructUrl(method), jsonRequest, listener, errorListener);
        requestQueue.add(objRequest);
    }

}