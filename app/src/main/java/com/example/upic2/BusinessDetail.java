package com.example.upic2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BusinessDetail extends AppCompatActivity {
    TextView tipoServicio,servicios,nombre;
    TextView marca,modelo,cilindraje,anio;
    String tipo="",id="",uid="";
    DatabaseReference datosReference= FirebaseDatabase.getInstance().getReference();
    BottomSheetDialog bottomSheetDialog;
    Button btnComentarios,btnPedir;
    DatabaseReference vehicleReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_detail);
        Intent i=getIntent();
        Bundle extras=i.getExtras();
        btnComentarios=findViewById(R.id.btnComentarios);
        btnPedir=findViewById(R.id.btnPedir);
        servicios=findViewById(R.id.servicios);
        tipoServicio=findViewById(R.id.tvTipoServicio);
        nombre=findViewById(R.id.nombreServicio);
        tipo=extras.getString("TIPO");
        id=extras.getString("ID");
        uid=extras.getString("UID");
        tipoServicio.setText("Servicio de "+tipo);
        datosReference.child("Servicios/"+tipo+"/"+id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("Nombre").getValue(String.class);
                String services=snapshot.child("Servicios").getValue(String.class);

                nombre.setText(name.toString());
                servicios.setText(services.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOpinion();
            }
        });
        btnPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmarDialog();
            }
        });
    }

    public void showOpinion(){
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_opinion,null);
        bottomSheetDialog=new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
    public void showConfirmarDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_reporte_problema,null);
        bottomSheetDialog=new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        marca=view.findViewById(R.id.marca);
        modelo=view.findViewById(R.id.modelo);
        cilindraje=view.findViewById(R.id.cilindraje);
        anio=view.findViewById(R.id.anio);
        loadVehicleData();
    }

    public void loadVehicleData(){
        vehicleReference.child("Usuarios/"+uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                marca.setText(snapshot.child("Marca").getValue(String.class));
                modelo.setText(snapshot.child("Modelo").getValue(String.class));
                cilindraje.setText(snapshot.child("Cilindraje").getValue(String.class));
                anio.setText(snapshot.child("Anio").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void callMap(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(i);
    }
    public void callConf(View view){
        Intent i = new Intent(getApplicationContext(), Confirm.class);

        startActivity(i);
    }
}