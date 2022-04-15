package com.example.evpoly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

public class User_view extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MainActivity";

    //타이머
    private Button startBTN;
    private Button stopBTN;
    private TextView timeView;


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
}