package com.example.api_restful_turriago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.api_restful_turriago.interfaces.UsersAPI_Retrofit;
import com.example.api_restful_turriago.modelos.Users;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String url = "https://gorest.co.in/";
    TextView textView;
    RequestQueue requestQueue;
    Button btnVolley;
    Button btnRetrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        btnVolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerRegistroVOLLEY();
                // find();
            }
        });

        btnRetrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // obtenerRegistro();
                find();
            }
        });
    }

    private void initUI(){
        btnVolley = findViewById(R.id.btnVolley);
        btnRetrofit = findViewById(R.id.btnRetrofit);
    }

    private void obtenerRegistroVOLLEY(){

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url + "public/v1/users",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int size = response.length();
                        response = fixEncoding(response);
                        JSONObject json_transform = null;

                        try {
                            if (size > 0)
                            {
                                json_transform = new JSONObject(response);
                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                Bundle b = new Bundle();
                                b.putString("json",json_transform.getString("data"));
                                intent.putExtras(b);
                                startActivity(intent);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("Accept", "application/json");
                return params;
            }
        };
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        } else {
            requestQueue.add(request);
        }
    }

    public static String fixEncoding(String response) {
        try {
            byte[] u = response.toString().getBytes(
                    "ISO-8859-1");
            response = new String(u, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    private void find(){


        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        UsersAPI_Retrofit journalAPI = retrofit.create(UsersAPI_Retrofit.class);
        Call<JSONObject> call = journalAPI.find();

        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                Log.d("respons",response.body().toString());
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }
}

