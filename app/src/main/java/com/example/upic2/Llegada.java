package com.example.upic2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Llegada extends AppCompatActivity {
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llegada);
    }
    public void callRep(View view){
        Intent i = new Intent(getApplicationContext(), Reportar.class);

        startActivity(i);
    }
    public void callProc(View view){
        Intent i = new Intent(getApplicationContext(), Proceso.class);

        startActivity(i);
    }
    public void showReportar(){
        View view = LayoutInflater.from(this).inflate(R.layout.activity_reportar,null);
        bottomSheetDialog=new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}