package com.example.evpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static String IP_ADDRESS = "223.26.138.80";
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

                String name = etName.getText().toString();
                String tel = etTel.getText().toString();
                String pw = etPw.getText().toString();
                String car = etCar.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://vmfhwprxm.dothome.co.kr" + "/userinsert.php", name,tel, pw, car);

                name = etName.getText().toString();
                etName.setText("");
                etTel.setText("");
                etPw.setText("");
                etCar.setText("");

                Toast.makeText(getApplicationContext(), "id : "+name +" 님의 회원가입이 완료 되었습니다.", Toast.LENGTH_LONG).show();

                break;
        }

    }

    // 주석

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(JoinActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
//            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String name = (String)params[1];
            String tel = (String)params[2];
            String pw = (String)params[3];
            String car = (String)params[4];

            String serverURL = (String)params[0];
            String postParameters = "name=" + name + "&tel=" + tel +"&pw="+ pw +"&car"+car;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}