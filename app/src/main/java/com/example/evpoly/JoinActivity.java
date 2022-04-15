package com.example.evpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "LoginActivity";

    private CheckBox userBox, managerBox;
    private Button checkBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_main);

        userBox = (CheckBox) findViewById(R.id.userBox);
        managerBox = (CheckBox) findViewById(R.id.managerBox);

        checkBTN = (Button) findViewById(R.id.checkBTN);

        userBox.setOnCheckedChangeListener(this);
        managerBox.setOnCheckedChangeListener(this);

        checkBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkBTN:
                if(managerBox.isChecked() == true && userBox.isChecked() == false){
                    Intent go = new Intent(this, LoginActivity.class);
                    finish();
                    startActivity(go);
                }
                else if(userBox.isChecked() == true && managerBox.isChecked() == false){
                    Intent go = new Intent(this, LoginActivity.class);
                    finish();
                    startActivity(go);
                }
                else {
                    Toast.makeText(this, "User 또는 Manager 중 하나를 선택하시오.", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    // 주석

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}