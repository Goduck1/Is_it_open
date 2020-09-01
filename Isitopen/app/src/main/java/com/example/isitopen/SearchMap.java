package com.example.isitopen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.Vector;

public class SearchMap extends FragmentActivity
implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private LocationManager locationManager;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private InfoWindow infoWindow = new InfoWindow();

    String typeN = new String();
    @SuppressLint("ServiceCast")
    @Override
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

        Intent intent = getIntent();
        typeN = intent.getStringExtra("typename");
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

//    public void onLocationChanged(Location location) {
//        if (naverMap == null || location == null) {
//            return;
//        }
//
//        LatLng coord = new LatLng(location);
//
//        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
//        locationOverlay.setVisible(true);
//        locationOverlay.setPosition(coord);
//        locationOverlay.setBearing(location.getBearing());
//
//        naverMap.moveCamera(CameraUpdate.scrollTo(coord));
//    }

    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {
        this.naverMap = naverMap;
        LatLng initialPosition = new LatLng(37.506855, 127.066242);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        naverMap.moveCamera(cameraUpdate);

        //카메라 이동 되면 호출되는 이벤트
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int reason, boolean animated) {
                //마커삭제먼저!
                freeActiveMarkers();
                //마커 생성 및 카메라 따라다니기
                final LatLng currentPosition = getCurrentPosition(naverMap);
                final Marker marker = new Marker();
                marker.setPosition(currentPosition);
                final double[] array = new double[2];
                array[0] = currentPosition.latitude;
                array[1] = currentPosition.longitude;
                marker.setMap(naverMap);
                activeMarkers.add(marker);
                //정보창 표시
                infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getApplication()) {
                    @NonNull
                    @Override
                    public CharSequence getText(@NonNull InfoWindow infoWindow) {
                        return "이 위치로 설정!";
                    }
                });
                //투명도 조정
//                infoWindow.setAlpha(0.9f);
                //인포창 표시
                infoWindow.open(marker);
                //마커클릭시 위치설정
                marker.setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        Intent intent = new Intent(getApplicationContext(), FilterActivity2.class);
                        intent.putExtra("newloc",array);
                        intent.putExtra("newTypeN",typeN);
                        startActivity(intent);
                        finish();
                        return false;
                    }
                });

            }
        });


        UiSettings uiSettings = naverMap.getUiSettings();
        //현위치버튼 활성화
        uiSettings.setLocationButtonEnabled(true); // 기본값 : false
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
       }

    // 마커 정보 저장시킬 변수들 선언
    private Vector<LatLng> markersPosition;
    private Vector<Marker> activeMarkers;

    //현재 카메라가 보고있는 위치
    public LatLng getCurrentPosition(NaverMap naverMap) {
        CameraPosition cameraPosition = naverMap.getCameraPosition();
        return new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
    }
    //마커삭제함수
    private void freeActiveMarkers() {
        if (activeMarkers == null) {
            activeMarkers = new Vector<Marker>();
            return;
        }
        for (Marker activeMarker: activeMarkers) {
            activeMarker.setMap(null);
        }
        activeMarkers = new Vector<Marker>();
    }
}