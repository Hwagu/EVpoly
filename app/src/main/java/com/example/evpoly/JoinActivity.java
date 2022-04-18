package com.example.evpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "LoginActivity";

    private CheckBox userBox, managerBox;
    private Button checkBTN;
    private EditText etName, etTel, etPw, etCar;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_main);

        userBox = (CheckBox) findViewById(R.id.userBox);
        managerBox = (CheckBox) findViewById(R.id.managerBox);

        checkBTN = (Button) findViewById(R.id.checkBTN);

        etName = (EditText) findViewById(R.id.etName);
        etTel = (EditText) findViewById(R.id.etTel);
        etPw = (EditText) findViewById(R.id.etPw);
        etCar = (EditText) findViewById(R.id.etCar);

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
                dataInsert();
                Toast.makeText(this, "눌렀다.", Toast.LENGTH_LONG).show();
                break;
        }

    }

    // 주석

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }

    public void dataInsert() {
        new Thread() {
            public void run() {
                try {
                    URL url = new URL("http://vmfhwprxm.dothome.co.kr/userinsert.php/");
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("name").append("=").append(etName.getText().toString()).append("/").append(etTel.getText().toString()).append("/").
                            append(etPw.getText().toString()).append("/").append(etCar.getText().toString()).append("/");

                    OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "utf-8");
                    osw.write(buffer.toString());
                    osw.flush();

                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "utf-8");
                    StringBuilder builder = new StringBuilder();
                    String str;
                    final BufferedReader reader = new BufferedReader(tmp);
                    while (reader.readLine() != null) {
                        System.out.println(reader.readLine());
                    }
                    while ((str = reader.readLine()) != null) {
                        builder.append(str = "\n");
                    }
                    String resultData = builder.toString();
                    final String[] sResult = resultData.split("/");
                        handler.post(r);

                        handler.post(r) {
                        etName.setText(sResult[0]);
                        etTel.setText(sResult[1]);
                        etPw.setText(sResult[2]);
                        etCar.setText(sResult[3]);
                    }
                } catch (Exception e) {
                    System.out.println("인터넷에 문제가 있습니다.");
                    Log.e("Error", "인터넷 문제 발생", e);
                }
            }
        }.start();
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
        }
    };
}