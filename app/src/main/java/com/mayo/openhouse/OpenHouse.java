package com.mayo.openhouse;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mayo.openhouse.datamodel.Comment;
import com.mayo.openhouse.datamodel.Openhouse;
import com.mayo.openhouse.datamodel.Property;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayo on 31/10/15.
 */
public class OpenHouse extends Application {

    private static OpenHouse mOpenHouse;
    private RequestQueue mQueue;

    public List<ParseUser> mUsers;
    public List<Comment> mComments;
    public List<Property> mProperty;
    public List<Openhouse> mOpenhouse;

    @Override
    public void onCreate() {
        super.onCreate();

        mOpenHouse = this;
        mQueue = Volley.newRequestQueue(this);
        mUsers = new ArrayList<>();
        mOpenhouse = new ArrayList<>();
        mProperty = new ArrayList<>();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Property.class);
        ParseObject.registerSubclass(Openhouse.class);
        Parse.initialize(this, "vBTCQZBN3KojEd1VFNaKa82mJ3hXVatZz2ioWrKT", "hIzFHhYHEgDOva3URprXF27jma0oXW1IMg6pdSFM");
    }

    public static OpenHouse getInstance() {
        return mOpenHouse;
    }

    //GET : 0 ; POST : 1
    public void requestJSONObject(final String url, int method, JSONObject params, Response.Listener<JSONObject> listener) {
        JsonObjectRequest request = new JsonObjectRequest(method, url, params, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(Tag.LOG, url + "JSONObjectRequest Error" + error);
            }
        });

        mQueue.add(request);
    }

    public void requestJSONArray(final String url, int method, JSONObject params, Response.Listener<JSONArray> listener) {
        Log.i(Tag.LOG,"Params: " + params);
        JsonArrayRequest requestArray = new JsonArrayRequest(method, url, params, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(Tag.LOG, url + "JSONArrayRequest Error" + error);
            }
        });
        mQueue.add(requestArray);
    }

}
