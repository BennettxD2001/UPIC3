package com.example.upic2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Proceso extends AppCompatActivity {
    BottomSheetDialog bottomSheetDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceso);
    }
    public void callRep(View view){
        Intent i = new Intent(getApplicationContext(), Reportar.class);

        startActivity(i);
    }
    public void callComen(View view){
        Intent i = new Intent(getApplicationContext(), Comentarios.class);

        startActivity(i);
    }
    public void showReportar(){
        View view = LayoutInflater.from(this).inflate(R.layout.activity_reportar,null);
        bottomSheetDialog=new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    public void showComen(){
        View view = LayoutInflater.from(this).inflate(R.layout.activity_comentarios,null);
        bottomSheetDialog=new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}