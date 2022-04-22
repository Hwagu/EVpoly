package com.example.evpoly;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evpoly.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager_view extends AppCompatActivity {

    private ImageView s1BTN, s2BTN, s3BTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_view);

        s1BTN = (ImageView) findViewById(R.id.s1BTN);
        s2BTN = (ImageView) findViewById(R.id.s2BTN);
        s3BTN = (ImageView) findViewById(R.id.s3BTN);

        s1BTN.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), Section1_view.class);
                        startActivity(intent);
                    }
                }
        );
    }
}



//    private ImageView profile;
//    private EditText addcarNum;
//    private EditText addName;
//    private Button addBTN;
//    private Button alertBTN;
//    private String Name;
//    private String carNum;
//    private RecyclerView mRecyclerView;
//    private MyRecyclerAdapter mRecyclerAdapter;
//    private ArrayList<FriendItem> mfriendItems; //목록 array List
//    ItemTouchHelper helper;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.manager_view);
//        profile = (ImageView) findViewById(R.id. profile);
//        addBTN = (Button) findViewById(R.id.addBTN);
//        alertBTN = (Button) findViewById(R.id.alertBTN);
//        addcarNum = (EditText) findViewById(R.id.addcarNum);
//        addName = (EditText) findViewById(R.id.addName);
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//
//        /* initiate adapter */
//        mRecyclerAdapter = new MyRecyclerAdapter();
//
//        /* initiate recyclerview */
//        mRecyclerView.setAdapter(mRecyclerAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//
//
//        //RecyclerView의 Adapter 세팅
//        mfriendItems = new ArrayList<>();
//        mRecyclerAdapter.setFriendList(mfriendItems);
////        for(int i=1;i<=10;i++){
////            if(i%2==0)
////                mfriendItems.add(new FriendItem(R.drawable.icon,i+"번째 사람",i+"번째 상태메시지"));
////            else
////                mfriendItems.add(new FriendItem(R.drawable.icon,i+"번째 사람",i+"번째 상태메시지"));
////        }
//        //ItemTouchHelper 생성
//
//        helper = new ItemTouchHelper(new ItemTouchHelperCallback(mRecyclerAdapter));
//        helper.attachToRecyclerView(mRecyclerView);
//
//
//
//    }
//    // 전기차가 아닐때 알림 띄우기
//    void showButtonAlertDialog()
//    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        //builder.setTitle("경고");
//        builder.setMessage("["+carNum+"] 전기차가 아닙니다!");
//        builder.setPositiveButton("예",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//
//        builder.show(); }
//    public void clickFunc(View view) {
//        switch (view.getId()) {
//            case R.id.addBTN:
//                Name = addName.getText().toString();
//                carNum = addcarNum.getText().toString();
//                mfriendItems.add(new FriendItem(Name, carNum));
//                if (carNum.startsWith("EV")==false)
//                {
//                    showButtonAlertDialog();
//                }
//                mRecyclerAdapter.notifyDataSetChanged();
//                addName.setText("");
//                addcarNum.setText("");
//                break;
//
//            case R.id.alertBTN:
//                Toast.makeText(this,"경보",Toast.LENGTH_SHORT).show();
//                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),notification);
//                ringtone.play();
//                break;
//
//        }
//    }