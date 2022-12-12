package com.example.upic2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener {
    GoogleMap gMap;
    BottomNavigationView navigationView;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    List<Servicios> mecanicos;
    List<Servicios> electricos;
    List<Servicios> vulcanizadoras;
    TextView tvMapaServicio;
    ImageButton btnLogout;
    FirebaseAuth mAuth;
    String uid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_UPIC2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mecanicos = new ArrayList<>();
        electricos = new ArrayList<>();
        vulcanizadoras = new ArrayList<>();
        navigationView = findViewById(R.id.nav_view);
        tvMapaServicio = findViewById(R.id.tvMapaServicio);
        btnLogout = findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();
        Intent i=getIntent();
        Bundle extras=i.getExtras();
        uid=extras.getString("UID");
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            public boolean onCreateOptionMenu(Menu menu){
                getMenuInflater().inflate(R.menu.botones, menu);
                return true;
            }

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_mecanicos:
                        loadMecanicosData(gMap);
                        tvMapaServicio.setText("Mecánicos Disponibles");
                        item.setChecked(true);
                        break;
                    case R.id.navigation_electricos:
                        loadElectricosData(gMap);
                        item.setChecked(true);
                        tvMapaServicio.setText("Eléctricos Disponibles");
                        break;
                    case R.id.navigation_vulcanizadoras:
                        loadVulcanizadorasData(gMap);
                        item.setChecked(true);
                        tvMapaServicio.setText("Vulcanizadoras Disponibles");
                        break;
                    case R.id.navigation_conf:
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS), 0);
                        return true;
                }
                return false;
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frame_ly1);
        mapFragment.getMapAsync(this);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
        });

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        this.gMap.setOnMapClickListener(this);
        this.gMap.setOnMapLongClickListener(this);
        double latitudes[] = {21.878622, 21.882745, 21.882364, 21.875810};
        double longitudes[] = {-102.262466, -102.259123, -102.267302, -102.264084};
        loadMecanicosData(gMap);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gMap.setMyLocationEnabled(true);
       /* for(int i=0;i<latitudes.length;i++){
            LatLng ita = new LatLng(latitudes[i],longitudes[i]);
            gMap.addMarker(new MarkerOptions().position(ita).title("Marker: "+i));
            gMap.moveCamera(CameraUpdateFactory.newLatLng(ita));
        }*/


    }
    public void loadMecanicosData(GoogleMap googleMap){
        googleMap.clear();
        reference.child("Servicios/Mecánicos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mecanicos.removeAll(mecanicos);
                for (DataSnapshot snapshot:
                     dataSnapshot.getChildren()) {
                    Servicios listap=snapshot.getValue(Servicios.class);
                    mecanicos.add(listap);
                    LatLng marker = new LatLng(listap.getLatitud(),listap.getLongitud());
                    googleMap.addMarker(new MarkerOptions().position(marker).title(listap.getNombre())).setTag(listap.getID());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Intent i=new Intent(MainActivity.this, BusinessDetail.class);
                i.putExtra("ID",marker.getTag().toString());
                i.putExtra("TIPO","Mecánicos");
                i.putExtra("UID",uid);
                startActivity(i);
                return false;
            }
        });
    }
    public void loadElectricosData(GoogleMap googleMap){
        googleMap.clear();
        reference.child("Servicios/Eléctricos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                electricos.removeAll(electricos);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    Servicios listap=snapshot.getValue(Servicios.class);
                    electricos.add(listap);
                    LatLng marker = new LatLng(listap.getLatitud(),listap.getLongitud());
                    googleMap.addMarker(new MarkerOptions().position(marker).title(listap.getNombre())).setTag(listap.getID());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Intent i=new Intent(MainActivity.this, BusinessDetail.class);
                i.putExtra("ID",marker.getTag().toString());
                i.putExtra("TIPO","Eléctricos");
                i.putExtra("UID",uid);
                startActivity(i);
                return false;
            }
        });
    }
    public void loadVulcanizadorasData(GoogleMap googleMap){
        googleMap.clear();
        reference.child("Servicios/Vulcanizadoras").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vulcanizadoras.removeAll(vulcanizadoras);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    Servicios listap=snapshot.getValue(Servicios.class);
                    vulcanizadoras.add(listap);
                    LatLng marker = new LatLng(listap.getLatitud(),listap.getLongitud());
                    googleMap.addMarker(new MarkerOptions().position(marker).title(listap.getNombre())).setTag(listap.getID());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Intent i=new Intent(MainActivity.this, BusinessDetail.class);
                i.putExtra("ID",marker.getTag().toString());
                i.putExtra("TIPO","Vulcanizadoras");
                i.putExtra("UID",uid);
                startActivity(i);
                return false;
            }
        });

    }
    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}