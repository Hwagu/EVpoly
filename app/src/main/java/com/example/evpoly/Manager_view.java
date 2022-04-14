package com.example.evpoly;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Manager_view<list> extends AppCompatActivity {

    //Debug Variable---------------------
    public static final String TAG = "Manager";
    private static final boolean DEBUG = true;

    // MEmber Variable-------------------
    private EditText carNumber;
    private Button okBTN;
    private Button resetBTN;
    private Button alertBTN;
    private String A;
    private ListView carList;
    ArrayList<String> car;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_view);

        //xml 파일에 존재하는 UI요소  객체에 저장하기 -> ID로 UI 요소를 찾아서 설정
        carNumber = (EditText) findViewById(R.id.carNumber);
        okBTN = (Button) findViewById(R.id.okBTN);
        resetBTN = (Button) findViewById(R.id.resetBTN);
        alertBTN = (Button) findViewById(R.id.alertBTN);
        carList = (ListView) findViewById(R.id.carList);
        carList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        car = new ArrayList<String>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, car);
        ListView listview = (ListView) findViewById(R.id.carList);
        carList.setAdapter(adapter);

    }


    void showButtonAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("경고");
        if (A.startsWith("EV")) {
            builder.setMessage("["+A+"] 전기차 입니다!");
        }
        else {
            builder.setMessage("["+A+"] 전기차가 아닙니다!");
           carList.setDivider(new ColorDrawable(Color.RED));
        }
            builder.setPositiveButton("예",
                    new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                }
            });

        builder.show(); }


    public void clickFunc(View view) {
        int check;
        int count = adapter.getCount();
        switch (view.getId()) {
            case R.id.okBTN:
                A = carNumber.getText().toString();
                car.add(carNumber.getText().toString());
                adapter.notifyDataSetChanged();
                showButtonAlertDialog();
                if (A.startsWith("EV")==false)
                {
                    carList.setBackgroundColor(Color.RED);
                }


                    break;

            case R.id.resetBTN:
                if (count > 0) {
                    check = carList.getCheckedItemPosition();
                    if (check > -1 && check < count) {
                        car.remove(check);
                        carList.clearChoices();
                        adapter.notifyDataSetChanged();

                    }
                }
                break;
            case R.id.alertBTN:
                Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vib.vibrate(1000);
                break;


        }
    }
}