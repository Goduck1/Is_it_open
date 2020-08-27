package com.example.isitopen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.CircleOverlay;
import com.naver.maps.map.overlay.Marker;

import java.util.Vector;

public class MapActivity extends FragmentActivity
    implements OnMapReadyCallback {


    private ViewGroup mapLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        //Filter액티비티에서 받아온 Info 클래스
        Info finding = (Info) getIntent().getSerializableExtra("class");



    }



    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true); // 기본값 : false


        double[] nowLoc = getIntent().getDoubleArrayExtra("loc");
        double latitude = nowLoc[0];
        double longitude = nowLoc[1];

        //마커표시
        Marker marker = new Marker();
        marker.setPosition(new LatLng(nowLoc[0], nowLoc[1]));
        marker.setMap(naverMap);

        //서클오버레이(반경) 표시하기
        CircleOverlay circle = new CircleOverlay();
        circle.setCenter(new LatLng(latitude,longitude));
        circle.setRadius(getIntent().getIntExtra("range",500)); // 받아오는 반경으로 변경하기
        //circle.setRadius(500);
        circle.setMap(naverMap);
        circle.setColor(Color.TRANSPARENT);
        circle.setOutlineColor(Color.BLACK);
        circle.setOutlineWidth(2);

        //카메라 초기 위치 설정
        LatLng initialPosition = new LatLng(nowLoc[0], nowLoc[1]);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        naverMap.moveCamera(cameraUpdate);

    }
    // 마커 정보 저장시킬 변수들 선언
    private Vector<LatLng> markersPosition;
    private Vector<Marker> activeMarkers;


    // 현재 카메라가 보고있는 위치
    public LatLng getCurrentPosition(NaverMap naverMap) {
        CameraPosition cameraPosition = naverMap.getCameraPosition();
        return new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
    }

}