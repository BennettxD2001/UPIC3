package com.example.upic2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Llegada extends AppCompatActivity {

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
}