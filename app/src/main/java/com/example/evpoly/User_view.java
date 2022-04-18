package com.example.evpoly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class User_view extends AppCompatActivity implements OnMapReadyCallback,  CompoundButton.OnCheckedChangeListener{
    private static final String TAG = "MainActivity";

    //타이머 시작 종류 버튼
    private Button startBTN;
    private Button stopBTN;
    //타이머 화면
    private TextView timeView;

    private CountDownTimer starttimer;
    private Switch powerswitch;
    private Switch powerupswitch;
    private ConstraintLayout timelayout;


    //지도
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationSource mlocatinSource;
    private NaverMap mNaverMap;
    private static String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_view);


        timeView = (TextView) findViewById(R.id.timeView);
        powerswitch =(Switch) findViewById(R.id.powerswitch);
        powerupswitch = (Switch) findViewById(R.id.powerupswitch);
        timelayout = (ConstraintLayout)findViewById(R.id.timelayout);
        stopBTN = (Button)findViewById(R.id.stopBTN);
        startBTN= (Button)findViewById(R.id.startBTN);


        // TODO : 타이머 돌릴 총시간
        String conversionTime = "000010";

        // 카운트 다운 시작
        countDown(conversionTime);

        //스위치
        powerswitch.setOnCheckedChangeListener(this);
        powerupswitch.setOnCheckedChangeListener(this);

        //타이머 초기 invisible
        timelayout.setVisibility(View.INVISIBLE);

       //지도 객체 생성
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.mapView);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.mapView, mapFragment).commit();
        }

        //getMapAsync를 호출 하여 비동기로 onMAPReady 콜백 메서드 호출
        //onMapReady에서 NaverMap 객체를 받아옴
        mapFragment.getMapAsync(this);

        //위치를 반환하는 fusedlocationsource 생성성
        mlocatinSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

    }




    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRAFFIC, true); // 실시간 교통정보


        Log.d(TAG, "onMapReady");

        //지도상에 마커 표시
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.5670135, 126.9783740));
        marker.setMap(naverMap);

        //navermap 객체 받앗 navermap 객체 에 위치 소스 지정
        mNaverMap = naverMap;
        naverMap.setLocationSource(mlocatinSource);     //현재위치

        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE);
        //현재 위치 표시할때 권한 확인/ 결과는 콜백 매서드 호출


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }


        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
        switch (compoundButton.getId()) {
            case (R.id.powerswitch):
                if (ischecked) {
                    timelayout.setVisibility(View.VISIBLE);
                    powerupswitch.setEnabled(false);
                    stopBTN.setEnabled(false);



                    startBTN.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            powerswitch.setEnabled(false);
                            startBTN.setEnabled(false);
                            stopBTN.setEnabled(true);



                            // 첫번쨰 인자 : 원하는 시간 (예를들어 30초면 30 x 1000(주기))
                            // 두번쨰 인자 : 주기( 1000 = 1초)
                            //1시간 = 3600000 14시간 =50400000
                            starttimer =  new CountDownTimer(50400000, 1000) {

                                // 특정 시간마다 뷰 변경
                                public void onTick(long millisUntilFinished) {

                                    // 시
                                    String hour = String.valueOf(millisUntilFinished / (60 * 60 * 1000));

                                    //분을 위한 나머지 = h_na
                                    long h_na = millisUntilFinished % (60 * 60 * 1000);
                                    //계산의 몫, 나머지 확인을 위한 로그
                                    Log.i(TAG, hour + "-" + h_na);

                                    //h_na의 몫 = 분
                                    String min = String.valueOf(h_na / (60 * 1000));

                                    //h_na의 나머지 = 분
                                    String second = String.valueOf((h_na % (60 * 1000)) / (1000));
                                    Log.i(TAG, hour + "-" + min + "-" + second);


                                    // 시간이 한자리면 0을 붙인다
                                    if (hour.length() == 1) {
                                        hour = "0" + hour;
                                    }

                                    // 분이 한자리면 0을 붙인다
                                    if (min.length() == 1) {
                                        min = "0" + min;
                                    }

                                    // 초가 한자리면 0을 붙인다
                                    if (second.length() == 1) {
                                        second = "0" + second;
                                    }

                                    timeView.setText(hour + ":" + min + ":" + second);
                                }

                                @Override
                                public void onFinish() {
                                    timeView.setText("시간종료!");
                                    startBTN.setEnabled(true);

                                }
                            }.start();

                        }

                    });


                    stopBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showEndAlertDialog();
                        }
                    });

                } else {
                    timelayout.setVisibility(View.INVISIBLE);
                    powerupswitch.setEnabled(true);
                }
                break;

            case (R.id.powerupswitch):
                if (ischecked) {
                    timelayout.setVisibility(View.VISIBLE);
                    powerswitch.setEnabled(false);


                    startBTN.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            startBTN.setEnabled(false);
                            powerupswitch.setEnabled(false);
                            starttimer = new CountDownTimer(3600000, 1000) {

                                // 특정 시간마다 뷰 변경
                                public void onTick(long millisUntilFinished) {

                                    // 시
                                    String hour = String.valueOf(millisUntilFinished / (60 * 60 * 1000));

                                    //분을 위한 나머지 = h_na
                                    long h_na = millisUntilFinished % (60 * 60 * 1000);
                                    //계산의 몫, 나머지 확인을 위한 로그
                                    Log.i(TAG, hour + "-" + h_na);

                                    //h_na의 몫 = 분
                                    String min = String.valueOf(h_na / (60 * 1000));

                                    //h_na의 나머지 = 분
                                    String second = String.valueOf((h_na % (60 * 1000)) / (1000));
                                    Log.i(TAG, hour + "-" + min + "-" + second);


                                    // 시간이 한자리면 0을 붙인다
                                    if (hour.length() == 1) {
                                        hour = "0" + hour;
                                    }

                                    // 분이 한자리면 0을 붙인다
                                    if (min.length() == 1) {
                                        min = "0" + min;
                                    }

                                    // 초가 한자리면 0을 붙인다
                                    if (second.length() == 1) {
                                        second = "0" + second;
                                    }

                                    timeView.setText(hour + ":" + min + ":" + second);
                                }

                                @Override
                                public void onFinish() {

                                    timeView.setText("시간종료!");

                                }
                            }.start();

                        }


                    });

                    stopBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showEndAlertDialog();


                        }

                    });



                } else {
                    timelayout.setVisibility(View.INVISIBLE);
                    powerswitch.setEnabled(true);
                }
                break;
        }
    }



    //충전 완료 버튼 눌릴 시 확인 팝업 띄우기
    void showEndAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("충전을 종료하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        powerswitch.setChecked(false);
                        powerupswitch.setEnabled(true);
                        powerupswitch.setChecked(false);
                        powerswitch.setEnabled(true);
                        timeView.setText("");
                        starttimer.cancel();
                        startBTN.setEnabled(true);
                        Toast.makeText(getApplicationContext(),"종료 되었습니다.",Toast.LENGTH_SHORT).show();


                    }
                });
        builder.setNegativeButton("아니요",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(getApplicationContext(),"충전을 계속 합니다.",Toast.LENGTH_SHORT).show();

                    }
                });
        builder.show();


    }



    //타이머 선언
    private void countDown(String time) {
        long conversionTime = 0;

        // 1000 단위가 1초
        // 60000 단위가 1분
        // 60000 * 3600 = 1시간

        String getHour = time.substring(0, 2);
        String getMin = time.substring(2, 4);
        String getSecond = time.substring(4, 6);

        // "00"이 아니고, 첫번째 자리가 0 이면 제거
        if (getHour.substring(0, 1) == "0") {
            getHour = getHour.substring(1, 2);
        }

        if (getMin.substring(0, 1) == "0") {
            getMin = getMin.substring(1, 2);
        }

        if (getSecond.substring(0, 1) == "0") {
            getSecond = getSecond.substring(1, 2);
        }

        // 변환시간
        conversionTime = Long.valueOf(getHour) * 1000 * 3600 + Long.valueOf(getMin) * 60 * 1000 + Long.valueOf(getSecond) * 1000;
    }

}
