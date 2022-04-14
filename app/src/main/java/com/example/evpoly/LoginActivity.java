package com.example.evpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "LoginActivity";

    private EditText id, pw;
    private CheckBox userBox, managerBox;
    private Button loginBTN;
    private TextView joinBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        id = (EditText) findViewById(R.id.id);
        pw = (EditText) findViewById(R.id.pw);

        userBox = (CheckBox) findViewById(R.id.userBox);
        managerBox = (CheckBox) findViewById(R.id.managerBox);

        loginBTN = (Button) findViewById(R.id.loginBTN);

        joinBTN = (TextView) findViewById(R.id.joinBTN);

        userBox.setOnCheckedChangeListener(this);
        managerBox.setOnCheckedChangeListener(this);

        loginBTN.setOnClickListener(this);
        joinBTN.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.loginBTN:

                case R.id.managerBox:
                    if (managerBox.isChecked() == true) {
                        Intent go = new Intent(this, Manager_view.class);
                        finish();
                        startActivity(go);
                    } else if (userBox.isChecked() && managerBox.isChecked() == true) {
                        Toast.makeText(this, "User 또는 Manager 중 하나를 선택하시오.", Toast.LENGTH_LONG).show();
                        break;
                    } else if (userBox.isChecked() && managerBox.isChecked() == false) {
                        Toast.makeText(this, "User 또는 Manager 중 하나를 선택하시오.", Toast.LENGTH_LONG).show();
                        break;
                    }

                case R.id.userBox:
                    if (userBox.isChecked() == true) {
                        Intent go = new Intent(this, User_view.class);
                        finish();
                        startActivity(go);
                    } else if (userBox.isChecked() && managerBox.isChecked() == true) {
                        Toast.makeText(this, "User 또는 Manager 중 하나를 선택하시오.", Toast.LENGTH_LONG).show();
                        break;
                    } else if (userBox.isChecked() && managerBox.isChecked() == false) {
                        Toast.makeText(this, "User 또는 Manager 중 하나를 선택하시오.", Toast.LENGTH_LONG).show();
                        break;
                    }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.joinBTN:
                Intent go = new Intent(this, JoinActivity.class);
                finish();
                startActivity(go);
                break;
        }
    }
}

//    @Override
//    public void onClick(View view) {
//
//        boolean checked = ((CheckBox) view).isChecked();
//
//        switch(view.getId()) {
//            case R.id.loginBTN:
//
//                case R.id.managerBox:
//                    if (managerBox.isChecked() == true) {
//                        Intent go = new Intent(this, Manager.class);
//                        finish();
//                        startActivity(go);
//                    }
//
//                    else if (userBox.isChecked() == true) {
//                        Intent go = new Intent(this, User.class);
//                        finish();
//                        startActivity(go);
//                    }
//
//                    else
//                        Toast.makeText(this, "User 또는 Manager 중 하나를 선택하시오.", Toast.LENGTH_LONG).show();
//
//        }
//    }

//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//            if (managerBox.isChecked() == true) {
//                Intent go = new Intent(this, Manager.class);
//                finish();
//                startActivity(go);
//            }
//
//            else if (userBox.isChecked() == true) {
//                Intent go = new Intent(this, User.class);
//                finish();
//                startActivity(go);
//            }
//
//            else
//                Toast.makeText(this, "User 또는 Manager 중 하나를 선택하시오.", Toast.LENGTH_LONG).show();
//    }
