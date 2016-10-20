package com.pollux.swiggy.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pollux.swiggy.R;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_home, HomeFragment.newInstance(), TAG).commit();
    }
}
