package com.example.flobizintern;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private ViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);

        mMainActivityViewModel = ViewModelProviders.of(this).get(ViewModel.class);

        mMainActivityViewModel.init();

        mMainActivityViewModel.getCities().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> nicePlaces) {
                mAdapter.notifyDataSetChanged();
            }
        });

        mMainActivityViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                else{
                    mProgressBar.setVisibility(View.GONE);
                    mRecyclerView.smoothScrollToPosition(mMainActivityViewModel.getCities().getValue().size()-1);
                }
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView(){
        mAdapter = new RecyclerAdapter(this, mMainActivityViewModel.getCities().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}
