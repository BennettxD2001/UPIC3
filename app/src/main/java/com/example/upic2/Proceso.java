package com.example.upic2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Proceso extends AppCompatActivity {

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
}