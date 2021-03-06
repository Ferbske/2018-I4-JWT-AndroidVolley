package com.appsfromholland.volleyapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.nameId);
        password = (EditText) findViewById(R.id.passwordId);
        result = (TextView) findViewById(R.id.statusId);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(
                        username.getText().toString(),
                        password.getText().toString()
                );
            }
        });
    }

    private void login(String username, String password) {

        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = "https://app-jwt.herokuapp.com/apiv2/login";

        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
            body.put("password", password);
        } catch(Exception e) {
            Log.e("VOLLEY_TAG", e.toString());
        }

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VOLLEY_TAG", response.toString());
                        result.setTextColor(Color.GREEN);
                        try {
                            result.setText(response.getString("token"));
                        } catch (JSONException e) {}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_TAG", error.toString());
                        result.setTextColor(Color.RED);
                        result.setText(error.toString());

                    }
                }
        );

        queue.add(request);
    }
}
