package com.example.evpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JoinActivity extends AppCompatActivity {

    private static final String TAG = "JoinActivity";

    DBHelper dbHelper;
    SQLiteDatabase database;
    private ImageView JoinBTN, checkBTN;
    private EditText etName, etId, etPw, etNum;

    private String tableName;
    private User user;
    private String alertNull;
    private SQLiteDatabase db;
    private boolean isCheck=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_main);

        user=new User();
        dbHelper=new DBHelper(this);
        db=dbHelper.getWritableDatabase();

        JoinBTN = (ImageView) findViewById(R.id.JoinBTN);
        checkBTN = (ImageView) findViewById(R.id.checkBTN);
        etName = (EditText) findViewById(R.id.etName);
        etId = (EditText) findViewById(R.id.etId);
        etPw = (EditText) findViewById(R.id.etPw);
        etNum = (EditText) findViewById(R.id.etNum);

        etId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                alertNull="중복확인을 해주세요 ";
                isCheck=false;
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        checkBTN.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if(checkId()) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(JoinActivity.this);
                            builder.setMessage("사용 가능한 아이디 입니다.")
                                    .setPositiveButton("확인",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    }).show();
                            isCheck=true;
                        }
                        else{

                            AlertDialog.Builder builder=new AlertDialog.Builder(JoinActivity.this);
                            builder.setMessage(alertNull)
                                    .setPositiveButton("확인",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    }).show();
                        }
                    }
                }
        );

        JoinBTN.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isCheck) {
                            if (isNull()) {
                                dbHelper.insertUser(user);

                                AlertDialog.Builder builder=new AlertDialog.Builder(JoinActivity.this);
                                builder.setMessage("가입이 완료되었습니다. 로그인 해주세요")
                                        .setPositiveButton("확인",new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                builder.setMessage(alertNull)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {


                                            }
                                        }).show();
                            }
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                            builder.setMessage(alertNull)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {


                                        }
                                    }).show();

                        }
                    }
                }
        );
    }

    private void createDatabase() {
        Toast.makeText(this, "호출됨", Toast.LENGTH_SHORT).show();

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();


        Toast.makeText(this, "생성함", Toast.LENGTH_SHORT).show();
    }

    private Boolean isNull(){
        String name=etName.getText().toString().trim();
        String pw=etPw.getText().toString().trim();
        String num=etNum.getText().toString().trim();

        if(name== null || name.equals("")){
            alertNull="이름을 입력하세요";

            return false;
        }
        else{
            user.setName(name);
        }
        if(pw== null || pw.equals("")){
            alertNull="비밀번호를 입력하세요";

            return false;
        }
        else{
            user.setPw(pw);
        }
        if(num== null || num.equals("")){
            alertNull="관리구역 번호를 입력하세요";

            return false;
        }  else{
            user.setNum(num);
        }

        return true;
    }


    private Boolean checkId(){
        String id=etId.getText().toString().trim();
        if(id== null ||id.equals("")){
            alertNull="아이디를 입력하세요";
            return false;
        }
        else{
            user.setId(id);
        }

        String sql="SELECT id FROM "+DBHelper.USER_TABLE_NAME;
        Cursor cursor =db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                String userid=cursor.getString(0);
                if(userid.equals(id)){
                    alertNull="중복되는 아이디입니다.";
                    return false;
                }
            }while(cursor.moveToNext());
        }

        return  true;
    }
}