package com.example.evpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase database;

    private EditText id, pw;
    private ImageView loginBTN, joinBTN;
    private CheckBox checkBox;
    private String ID, PW;
    private String alertNull;

    private SQLiteDatabase db;

    public static final String Column_id = "id";
    public static final String Column_pw = "pw";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.login_main);

        id = (EditText)findViewById(R.id.id);
        pw = (EditText)findViewById(R.id.pw);

        loginBTN = (ImageView) findViewById(R.id.loginBTN);
        joinBTN = (ImageView) findViewById(R.id.joinBTN);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        dbHelper = new DBHelper(this);
        db=dbHelper.getReadableDatabase();

        joinBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);

                startActivity(intent);
            }
        });

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ID = id.getText().toString();
                PW = pw.getText().toString();

                Log.d(getClass().getName(),"온클릭해서들어가는값"+ID+","+PW);


                if (loginidCheck(ID, PW)){
                    Toast.makeText(getApplicationContext(),"로그인완료",Toast.LENGTH_LONG).show();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("id", ID);

                    if(checkBox.isChecked() == true){
                        SaveSharedPreference.setUserId(LoginActivity.this, ID);
                    }

                    Intent intent = new Intent(getApplicationContext(), Manager_view.class);
                    intent.putExtras(bundle1);
                    startActivity(intent);


                }
                else {
                    Toast.makeText(getApplicationContext(),"id 또는 pw가 틀렸습니다",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean loginidCheck(String id, String pw) {
        if(id== null ||id.equals("")){
            alertNull="아이디를 입력하세요";
            return false;
        }
        else{
        }

        String sql="SELECT name FROM "+DBHelper.USER_TABLE_NAME + " where " + Column_id
                + "= '"+ id +"' and " + Column_pw + "= '" + pw + "'";

        Cursor cursor =db.rawQuery(sql,null);
        if(cursor.getCount()==0){
            return false;
        }

        return  true;
    }
}

