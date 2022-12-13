package com.example.upic2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;

public class Login extends AppCompatActivity {
    EditText correo, contra;
    String _url = "https://www.facebook.com/erik.mares.104";
    Button iniciar, registrarse,_btn_contacto;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        correo=findViewById(R.id.correo);
        contra=findViewById(R.id.contra);
        iniciar=findViewById(R.id.inic);
        registrarse=findViewById(R.id.registrarse);
        _btn_contacto=findViewById(R.id.btn_contacto);
        mAuth = FirebaseAuth.getInstance();

        _btn_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri _link = Uri.parse(_url);
                Intent i = new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);

            }
        });
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(correo.getText().toString(),contra.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i=new Intent(Login.this, MainActivity.class);
                            String uid=mAuth.getCurrentUser().getUid();
                            i.putExtra("UID", uid);
                            startActivity(i);
                            finish();
                        }
                    }
                });
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Login.this, SignIn.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= mAuth.getCurrentUser();
        if (user != null){
            Intent i=new Intent(Login.this, MainActivity.class);
            String uid=mAuth.getCurrentUser().getUid();
            i.putExtra("UID", uid);
            startActivity(i);
            finish();
        }
    }

}