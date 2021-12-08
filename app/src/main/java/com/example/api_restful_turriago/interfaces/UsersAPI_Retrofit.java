package com.example.api_restful_turriago.interfaces;

import com.example.api_restful_turriago.modelos.Users;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface UsersAPI_Retrofit {
    @GET("public/v1/users")
    Call<JSONObject> find();
}


