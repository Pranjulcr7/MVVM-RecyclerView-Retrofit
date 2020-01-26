package com.example.flobizintern;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private MutableLiveData<List<City>> mCities;
    private Repository Repo;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();

    public void init(){

        if(mCities != null){
            return;
        }

        Repo = Repository.getInstance();
        mCities = Repo.getCities();
    }

    @SuppressLint("StaticFieldLeak")
    public void addNewValue(final City city){

        isUpdating.setValue(Boolean.TRUE);

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected void onPostExecute(Void aVoid){

                super.onPostExecute(aVoid);
                List<City> currentCities = mCities.getValue();
                currentCities.add(city);
                mCities.postValue(currentCities);
                isUpdating.postValue(Boolean.FALSE);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute();
    }

    public LiveData<List<City>> getCities(){
        return mCities;
    }

    public LiveData<Boolean> getIsUpdating(){
        return isUpdating;
    }

}
