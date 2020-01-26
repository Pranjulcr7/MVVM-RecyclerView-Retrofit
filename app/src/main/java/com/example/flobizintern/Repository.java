package com.example.flobizintern;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private static Repository instance;
    private ArrayList<City> dataset = new ArrayList<>();

    public static Repository getInstance(){

        if(instance == null){

            instance = new Repository();
        }
        return instance;
    }

    public MutableLiveData<List<City>> getCities(){
        setCities();
        MutableLiveData<List<City>> data = new MutableLiveData<>();
        data.setValue(dataset);
        return data;
    }

    private void setCities(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.myjson.com/bins/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<City>> call = jsonPlaceHolderApi.getCities();

        call.enqueue(
                new Callback<List<City>>() {
                    @Override
                    public void onResponse(Call<List<City>> call, Response<List<City>> response) {

                        System.out.println(response);
                        List<City> Cities = response.body();

                        for (City city : Cities){
                            System.out.println("City name: "+city.getName());
                            dataset.add(
                                    new City(city.getName())
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call<List<City>> call, Throwable t) {

                    }
                }
        );
    }

}
