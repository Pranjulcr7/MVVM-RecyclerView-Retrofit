package com.example.flobizintern;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("lw9qf")
    Call<List<City>> getCities();
}
