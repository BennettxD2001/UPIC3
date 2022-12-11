package com.example.upic2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Confirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
    }
    public void callMap(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(i);
    }
    public void callLleg(View view){
        Intent i = new Intent(getApplicationContext(), Llegada.class);

        startActivity(i);
    }
}