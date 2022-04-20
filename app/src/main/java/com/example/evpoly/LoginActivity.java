package com.example.evpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    DBHelper dbHelper;
    SQLiteDatabase db;
    String databaseName;
    private String alertNull;

    private EditText id, pw;
    private Button loginBTN, joinBTN;

    String Text;
    String Text2;
    String ID;
    String PW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        id = (EditText) findViewById(R.id.id);
        pw = (EditText) findViewById(R.id.pw);

        loginBTN = (Button) findViewById(R.id.loginBTN);
        joinBTN = (Button) findViewById(R.id.JoinBTN);

        databaseName = "diary";

        joinBTN.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent goJoin = new Intent(getApplicationContext(), JoinActivity.class);
                        startActivity(goJoin);
                    }
                }
        );

        loginBTN.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (db != null) {
                            Cursor cursor = db.rawQuery("SELECT name, major FROM " + "PORODUCT", null);
                            cursor.moveToNext();
                            ID = cursor.getString(0);
                            PW = cursor.getString(1);
                            Text = id.getText().toString();
                            Text2 = pw.getText().toString();
                            if (Text.equals(ID) && Text2.equals(PW)) {
                                Intent goManager = new Intent(getApplicationContext(), Manager_view.class);
                                startActivity(goManager);
                                Toast.makeText(getApplicationContext(), Text + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            cursor.close();
                        }
                    }
                }
        );

    }

}

