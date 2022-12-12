package com.example.upic2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Comentarios extends AppCompatActivity {
    String uid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
    }
    public void callMap(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("UID",uid);
        startActivity(i);
    }

}