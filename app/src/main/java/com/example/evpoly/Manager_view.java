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
                        Intent intent1 = new Intent(getApplicationContext(), Section1_view.class);
                        startActivity(intent1);
                    }
                }
        );

        s2BTN.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent2 = new Intent(getApplicationContext(), Section2_view.class);
                        startActivity(intent2);
                    }
                }
        );

        s3BTN.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent3 = new Intent(getApplicationContext(), Section3_view.class);
                        startActivity(intent3);
                    }
                }
        );
    }
}
