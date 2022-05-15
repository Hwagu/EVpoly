package com.example.evpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_main);

        if(SaveSharedPreference.getUserId(StartActivity.this).length()==0){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        else{
            Intent intent = new Intent(getApplicationContext(), Manager_view.class);
            intent.putExtra("userID", SaveSharedPreference.getUserId(StartActivity.this).toString());
            startActivity(intent);
            finish();
        }

    }



}